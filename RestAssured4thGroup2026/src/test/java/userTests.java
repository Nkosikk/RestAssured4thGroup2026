import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.userRequestBuilder;

public class userTests {

    static String firstName = "Jim";
    static String lastName = "Jones";
    static String email = "jmonate@example.com";
    static String password = "#12345678";
    static String groupId = "cdc7d817-b518-4d0c-a9fa-b8ae0585cd64";

    //static Faker faker = new Faker();

//    public static void setupData(){
//        firstName = faker.name().firstName();
//        lastName = faker.name().lastName();
//        email = "Ituk" + faker.internet().emailAddress();
//        password = "#12345678";
//        groupId = "92833dab-c6eb-41ac-bc8c-dbe6b35d58e3";
//    }


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
@Test(dependsOnMethods = "testAdminLogin")
    public void userApproval(){
        Response response = AdminRequestBuilder.userApproval();
        response.then().log().all();

       Assert.assertEquals(response.getStatusCode(),200);
    }

}
