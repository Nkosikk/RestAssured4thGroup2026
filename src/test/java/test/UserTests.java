package test;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.UserRequestBuilder;
import utils.DBConnection;

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
    public static void setupData() throws SQLException {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = "Group4" + faker.internet().emailAddress(); //unique email for each test run
        password = "7654321!";
        groupId = "0d4364c2-3476-44dc-abfb-cc901b254ef2";

        DBConnection.insertUser(email, password); // Insert the user into the database
        DBConnection.getLoginDetails(email); // Retrieve the login details from the database

    }
    @Test
    public void testUserRegistration() {
        Response response = UserRequestBuilder.userRegistrationRequest(firstName, lastName, email, password, groupId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 201);
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
    public void testRegisteredUserLogin(){
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


}
