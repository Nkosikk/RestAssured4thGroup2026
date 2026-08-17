package test;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.UserRequestBuilder;

import static org.hamcrest.Matchers.equalTo;

public class UserTests {

    //declaring variables to be used in the tests
    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static String groupId;

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


}
