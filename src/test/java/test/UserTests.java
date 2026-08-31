package test;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.UserRequestBuilder;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserTests {

    //declaring variables to be used in the tests
    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static String groupId;

    static Faker faker = new Faker();

    //initializing the variables with random data before running the tests
    @BeforeClass
    public static void setupData() {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = "Group4" + faker.internet().emailAddress(); //unique email for each test run
        password = "7654321!";
        groupId = "fa37dc11-f688-4ce6-ab78-b2014ee9e199";
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
    public static void testUserLogin() {
        Response response = UserRequestBuilder.userLogin(email, password);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        //get token from response and save it in a static variable for future use
        String userToken = response.jsonPath().getString("data.token");
    }


}
