package requestBuilder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloadBuilder.userPayLoad;
import static io.restassured.RestAssured.given;
import static commons.Routes.BASE_URL;


public class userRequestBuilder {
    static String registeredUserId;
    static String userToken;
    public static Response userRegistrationRequest(String firstname, String lastName, String email, String password, String groupId){

        String apiPath = "/APIDEV/register";
        Response response  = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType(ContentType.JSON)
                .body(userPayLoad.userRegistrationPayload(firstname,lastName,email,password,groupId))
                .when()
                .post()
                .then()
                .extract().response();

        registeredUserId = response.jsonPath().getString("data.id");

        return response;
    }

    public static Response userLogin(String email, String password){
        String apiPath = "/APIDEV/login";
        Response response  = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType(ContentType.JSON)
                .body(userPayLoad.loginPayload(email, password))
                .when()
                .post()
                .then()
                .extract().response();

        userToken = response.jsonPath().getString("data.token");
        return response;

    }
}
