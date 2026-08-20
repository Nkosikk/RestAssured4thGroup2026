package test;

import com.github.javafaker.Faker;
import commons.Routes;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserTests {

    //declaring variables to be used in the tests
    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static String groupId;
    static String invalidUsername = "InvalidUser";
    static String invalidPassword = "InvalidPass";

    static Faker faker = new Faker();

    //initializing the variables with random data before running the tests
    @BeforeClass
    public static void setupData() {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = "Group4" + faker.internet().emailAddress(); //unique email for each test run
        password = "7654321!";
        groupId = "0d4364c2-3476-44dc-abfb-cc901b254ef2";


    }
    @Test
    public void testUserRegistration() throws SQLException {
        Response response = UserRequestBuilder.userRegistrationRequest(firstName, lastName, email, password, groupId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 201);
        DBConnection.insertUser(email, password); // Insert the user into the database
    }

    @Test
    public void testAdminLogin() {
        Response response = AdminRequestBuilder.adminLogin();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (dependsOnMethods = {"testUserRegistration","testAdminLogin"})
    public void testUserApproval() {
//        Response response = AdminRequestBuilder.UserApproval();
//        response.then().log().all();
//
//        Assert.assertEquals(response.getStatusCode(), 200);

        AdminRequestBuilder.UserApproval()
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("success", equalTo(true))
                    .body("data.approvalStatus", equalTo("approved"));
    }

    @Test (dependsOnMethods = {"testUserApproval"})
    public void testRegisteredUserLogin() throws SQLException {
        DBConnection.getLoginDetails(email); // Retrieve the login details from the database
       // UserRequestBuilder.userLogin(email, password) - Using the registered email and password from the setupData method
        UserRequestBuilder.userLogin(DBConnection.emailFromDB, DBConnection.passwordFromDB)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.token", notNullValue());
    }

    @Test
    public void InvalidLoginTest(){
        //UserRequestBuilder.userLogin(invalidUsername, invalidPassword)
            UserRequestBuilder.userLogin("invalidUsername", "invalidPassword")
                .then()
                .log().all()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("error_code", equalTo("INVALID_CREDENTIALS"));
    }

    @Test //(dependsOnMethods = {"testUserRegistration"})
    public void schemaValidationTest() {

        Response response  =  UserRequestBuilder.userRegistrationRequest(firstName, lastName, email, password, groupId);

        try{
            String savedSchema = Files.readString(
                    Paths.get(Routes.JSON_SCHEMA_PATH+ "UserRegistrationSchema.json"));
            System.out.println("Loaded JSON schema:\n" + savedSchema);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String schemaPath = Paths.get(Routes.JSON_SCHEMA_PATH + "UserRegistrationSchema.json").toAbsolutePath().toString();
        response.then().assertThat().body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema(Paths.get(schemaPath).toFile()));

    }

    //Fix the UserApproval Schema test to validate the response schema for the UserApproval API

    @Test
    public void getCourseTest(){

        AdminRequestBuilder.getCourse("beginner")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }
}
