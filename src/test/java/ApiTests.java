import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import com.api.utilities.ConfigReader;

public class ApiTests {

    String baseUrl = "https://ndosiautomation.co.za/APIDEV";
    String authToken;

    @Test(priority = 1)
    public void loginTest() {
        // Test code for login API
        String payload = "{\n" +
                "  \"email\": \"+ email +\",\n" +
                "  \"password\": \"+ password +\"\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .basePath("/login")
                .header("Accept", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();

        // Extract the auth token from the response
authToken = response.jsonPath().getString("data.token");

// Print the response code
        int ResponseCode = response.getStatusCode();
        assert ResponseCode == 200 : "Expected response code 200 but got " + ResponseCode;
        System.out.println("Response Code: " + ResponseCode);
        // authToken = response.jsonPath().getString("token");
    }

    @Test (dependsOnMethods = "loginTest")
    public void loginTestWithInvalidCredentials() {

        // Test code for login API
        String payload = "{\n" +
                "  \"email\": \"jugzin@gmail.com\",\n" +
                "  \"password\": \"Cjsin@1233\"\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .basePath("/login")
                .header("Accept", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();

        // Extract the auth token from the response
        //authToken = response.jsonPath().getString("data.token");

// Print the response code
        int ResponseCode = response.getStatusCode();
        assert ResponseCode == 401 : "Expected response code 200 but got " + ResponseCode;
        //System.out.println("Response Code: " + ResponseCode);
        authToken = response.jsonPath().getString("token");
    }

    @Test (dependsOnMethods = "loginTest")
    public void createTestimonialTest() {
        // Test code for creating a testimonial
        String payload = "{\n" +
                "  \"title\": \"YES\",\n" +
                "  \"content\": \"Im testing\",\n" +
                "  \"rating\": 5,\n" +
                "  \"isPublic\": true\n" +
                "}";

String path = "/testimonials";
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .basePath(path)
                .header("Authorization", "Bearer " + authToken)
                .body(payload)
                .when()
                .log().all()
                .post().prettyPeek();

        // Print the response code
        int ResponseCode = response.getStatusCode();
        assert ResponseCode == 201 : "Expected response code 201 but got " + ResponseCode;
        System.out.println("Response Code: " + ResponseCode);
    }

    @Test (dependsOnMethods = "loginTest")
    public void getMyTestimonialTest() {
        // Test code for getting a testimonial
        String path = "/my-testimonials";
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .basePath(path)
                .header("Authorization", "Bearer " + authToken)
                .when()
                .log().all()
                .get().prettyPeek();

        // Print the response code
        int ResponseCode = response.getStatusCode();
        assert ResponseCode == 200 : "Expected response code 200 but got " + ResponseCode;
        System.out.println("Response Code: " + ResponseCode);
    }
}

