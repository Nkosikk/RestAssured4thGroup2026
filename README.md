# RestAssured4thGroup2026

API test automation suite using RestAssured, TestNG and Maven. Tests produce Allure results for reporting.

## Summary
This repository contains automated API tests implemented with RestAssured (Java) and TestNG. Tests exercise user and admin flows, validate JSON schemas, and can interact with a MySQL test database.

## Requirements
- Java 21 (JDK)
- Maven
- (Optional) Allure CLI for serving reports

## Key dependencies (from pom.xml)
- io.rest-assured: rest-assured
- org.testng: testng
- io.qameta.allure: allure-testng
- io.rest-assured: json-schema-validator

## Configuration
- Base URL, JSON schema path, and database connection are configured in src/test/java/commons/Routes.java.
- IMPORTANT: Do NOT commit production or sensitive credentials. Replace Routes.java values with environment-based loading or a properties file for CI and local runs.

## Run tests
- Run full test suite:
  mvn clean test

- Run a single TestNG test class or method:
  mvn -Dtest=test.UserTests#testUserRegistration test

- Output locations:
  - target/allure-results (Allure raw results)
  - target/surefire-reports (TestNG/Surefire reports)

## Allure report
- With Allure CLI installed:
  allure serve target/allure-results

- In CI, publish the contents of target/allure-results using your CI provider's Allure plugin or a static-artifact approach.

## Project layout (relevant folders)
- pom.xml — Maven configuration
- src/test/java — test sources
  - commons — shared routes/config
  - requestBuilder — RestAssured request builders for user/admin flows
  - payloadBuilder — JSON payload helpers
  - utils — helpers (DB connection, etc.)
  - test — TestNG test classes (e.g., UserTests.java)
- src/test/resources/schemas — JSON schemas used for validation
- target — build and test outputs
- allure-results — (archived/test run output)

## Notes & best practices
- Tests currently reference DB settings in Routes.java; prefer environment variables for secrets.
- Keep tests idempotent and avoid depending on mutable shared state where possible.
- If you need to run a subset of tests regularly, create TestNG groups or suites to simplify filtering.

## Contributing
Fork, create a branch, and submit pull requests. Ensure tests pass locally (mvn clean test) and include updates to README if you add new test suites or dependencies.

## License
Add a license as appropriate for your project.

