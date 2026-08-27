package UserTests;

import Utils.DBConnection;
import com.github.javafaker.Faker;
import commons.Routes;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.userRequestBuilder;
import requestBuilder.utilityRequestBuilder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class UserTests {

    //Declaring variables to be used in the test
    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static String groupId;
    static String invalidUsername = "invalidUsername";
    static String invalidPassword = "invalidPassword";


    static Faker faker = new Faker();

    //Initializing random variables with data before running the tests
    @BeforeTest
    public static void setupData() {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = "Ituk" + faker.internet().emailAddress();
        password = "#12345678";
        groupId = "92833dab-c6eb-41ac-bc8c-dbe6b35d58e3";

    }

    @Test
    public void testUserRegistration() throws SQLException {
        Response response = userRequestBuilder.userRegistrationRequest(firstName, lastName, email, password, groupId);
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

    @Test(dependsOnMethods = {"testUserRegistration", "testAdminLogin"})
    public void testUserApproval() {
        Response response = AdminRequestBuilder.userApproval();
        response.then().log().all();

        //Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(dependsOnMethods = "testUserApproval")
    public void testRegisteredUseLogin() throws SQLException {
        DBConnection.getLoginDetails(email);
        //userRequestBuilder.userLogin(email,password) Using the registered email and password from the setup methods
        userRequestBuilder.userLogin(DBConnection.emailFromDB, DBConnection.passwordFromDB)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.token", notNullValue());

    }

    @Test
    public void invalidLoginTest() {
        userRequestBuilder.userLogin(invalidUsername, invalidPassword)
                .then()
                .log().all()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("error_code", equalTo("INVALID_CREDENTIALS"));
    }

    @Test
    public void utilityTest() {
        utilityRequestBuilder.utility()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

@Test
    public void schemaValidationTest() {
        String schemaEmail = "schema_test"+faker.internet().emailAddress();
        Response response = userRequestBuilder.userRegistrationRequest(firstName, lastName, schemaEmail, password, groupId);

        try {
            String savedSchema = Files.readString(Paths.get(Routes.JsonSchemaPath + "userRegistrationSchema.json"));
            System.out.println("Saved Schema: " + savedSchema);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String schemaPath = Paths.get(Routes.JsonSchemaPath, "userRegistrationSchema.json").toString();
        response.then().assertThat().body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema(Paths.get(schemaPath).toFile()));
    }

    @Test
    public void GetCourseTest() {
        AdminRequestBuilder.getCourse("beginner")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }
}

