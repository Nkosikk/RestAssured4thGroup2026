package tests;


import io.restassured.response.Response;

import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;


import static requestBuilder.UserRequestBuilder.userRegistrationRequest;

public class UserTests {

    static String firstname = "James";
    static String lastname = "Bond";
    static String email = "bond39@example.com";
    static String password = "bond@007";
    static String groupId = "5328c91e-fc40-11f0-8e00-5000e6331276";

    /*static String firstname;
    static String lastname;
    static String email;
    static String password;
    static String groupId;
    static Faker fake = new Faker();

    public static void setUpData() {
        firstname = fake.name().firstName();
        lastname = fake.name().lastName();
        email = fake.internet().emailAddress();
        password = fake.internet().password(8, 16);
        groupId = "5328c91e-fc40-11f0-8e00-5000e6331276"; // Assuming groupId is a String, you can change it as needed
    }*/

    @Test
    public void userRegistrationTest() {

        Response response = userRegistrationRequest(firstname, lastname, email, password, groupId);
        response.then().log().all();

        int statusCode = response.getStatusCode();
        assert statusCode == 201 : "Expected status code 200 but got " + statusCode;

    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void adminLoginTest() {
        Response response = AdminRequestBuilder.adminLoginRequest();
        response.then().log().all();

    }


    @Test(dependsOnMethods = "adminLoginTest")
    public void approveUserTest() {
        Response response = AdminRequestBuilder.approveUserRequest();
        response.then().log().all();

        int statusCode = response.getStatusCode();
        assert statusCode == 200 : "Expected status code 200 but got " + statusCode;
    }
}


