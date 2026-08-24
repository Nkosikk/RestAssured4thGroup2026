package test;

import com.github.javafaker.Faker;
import commons.Routes;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.UserRequestBuilder;
import utils.DBConnection;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserTests {

    //declaring variables to be used in the tests
    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static String groupId;
    static String invalidUsername = "InvalidUsername";
    static String invalidPassword = "InvalidPassword";

    static Faker faker = new Faker();

    //initialising the variables with random data before running tests
    @BeforeClass
    public static void setupData() {
          firstName = faker.name().firstName();
          lastName = faker.name().lastName();
          email = "Group4" + faker.internet().emailAddress();//unique email for each test run
          password = "1234567!";
          groupId = "0d4364c2-3476-44dc-abfb-cc901b254ef2";

    }
    @Test
    public void testUserRegistration() throws SQLException {
        Response response = UserRequestBuilder.userRegistrationRequest(firstName, lastName, email, password, groupId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 201);
        DBConnection.insertUser(email, password);

    }

    @Test
    public void testAdminLogin() {
        Response response = AdminRequestBuilder.adminLogin();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (dependsOnMethods = {"testUserRegistration", "testAdminLogin"})
    public void testUserApproval() {
        //       Response response = AdminRequestBuilder.UserApproval();
        //       response.then().log().all();

        //       Assert.assertEquals(response.getStatusCode(), 200);

        AdminRequestBuilder.UserApproval()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.approvalStatus", equalTo("approved"));
    }


    @Test //(dependsOnMethods = {"testUserApproval"})
    public void testRegisteredUserLogin() throws SQLException {
        DBConnection.getLoginDetails(email);
       // UserRequestBuilder.userLogin(email,password)
        UserRequestBuilder.userLogin(DBConnection.emailFromDB, DBConnection.passwordFromDB)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.token", notNullValue());
    }


    @Test
    public void  InvalidLoginTest() {
        // UserRequestBuilder.userLogin(invalidUsername,invalidPassword)
        UserRequestBuilder.userLogin("invalidUsername", "invalidPassword")
                .then()
                .log().all()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("error_code", equalTo("INVALID_CREDENTIALS"));

    }



    @Test
    public void schemaValidationTest() {
        // Implement schema validation test here

        Response response = UserRequestBuilder.userRegistrationRequest(firstName, lastName, email, password, groupId);

        try{
            String savedSchema = Files.readString(
                    Paths.get(Routes.JSON_SCHEMA_PATH + "UserRegistrationSchema.json"));
            System.out.println("Loaded JSON schema:\n" + savedSchema);
        }
        catch (Exception e) {
            e.printStackTrace();

        }


        String schemaPath = Paths.get(Routes.JSON_SCHEMA_PATH + "UserRegistrationSchema.json").toAbsolutePath().toString();
        response.then().assertThat().body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema(Paths.get(schemaPath).toFile()));
    }


    @Test
    public void getCourseTest() {
        AdminRequestBuilder.getCourses("beginner")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));

    }










    }









