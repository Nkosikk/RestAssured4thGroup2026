package test;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.UserRequestBuilder;

public class UserTests {

    static String firstName = "John ";
    static String lastName = "Doe";
    static String email = "testAPI2026" + System.currentTimeMillis() + "@example.com";
    static String password = "1234567!";
    static String groupId = "5328c91e-fc40-11f0-8e00-5000e6331276";

 //   static Faker faker = new Faker();

//    public static void setupData() {
//        firstName = faker.name().firstName();
//        lastName = faker.name().lastName();
//        email = "Group4" + faker.internet().emailAddress(); //unique email for each test run
//        password = "7654321!";
//        groupId = "0d4364c2-3476-44dc-abfb-cc901b254ef2";
//    }
    @Test
    public void testUserRegistration() {
        Response response = UserRequestBuilder.userRegistrationRequest(firstName, lastName, email, password, groupId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test (dependsOnMethods = "testUserRegistration")
    public void testAdminLogin() {
        Response response = AdminRequestBuilder.adminLogin();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (dependsOnMethods = "testAdminLogin")
    public void testUserApproval() {
        Response response = AdminRequestBuilder.UserApproval();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }


}
