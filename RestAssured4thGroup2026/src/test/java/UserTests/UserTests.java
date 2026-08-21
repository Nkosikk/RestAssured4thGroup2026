package UserTests;

import Utils.BDConnection;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.userRequestBuilder;
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
   public static void setupData(){
       firstName = faker.name().firstName();
       lastName = faker.name().lastName();
        email = "Ituk" + faker.internet().emailAddress();
       password = "#12345678";
      groupId = "92833dab-c6eb-41ac-bc8c-dbe6b35d58e3";


        DBConnection.insertUser(email,password);
        DBConnection.getLoginDetails(email);
   }

@Test
    public void testUserRegistration(){
        Response response = userRequestBuilder.userRegistrationRequest(firstName,lastName,email,password,groupId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 201);
    }
@Test
    public void testAdminLogin(){
        Response response = AdminRequestBuilder.adminLogin();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
@Test(dependsOnMethods = {"testUserRegistration","testAdminLogin"})
    public void testUserApproval(){
        Response response = AdminRequestBuilder.userApproval();
        response.then().log().all();

       Assert.assertEquals(response.getStatusCode(),200);
    }
    @Test(dependsOnMethods = "testUserApproval")
    public  void testRegisteredUseLogin(){
        //userRequestBuilder.userLogin(email,password) Using the registered email and password from the setup methods
        userRequestBuilder.userLogin()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.token", notNullValue());

    }
@Test
    public void invalidLoginTest(){
        userRequestBuilder.userLogin(invalidUsername,invalidPassword)
                .then()
                .log().all()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("error_code",equalTo("INVALID_CREDENTIALS"));
    }

    }

