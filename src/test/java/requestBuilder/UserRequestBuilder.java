package requestBuilder;


import io.restassured.response.Response;

import static commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;
import static payloadBuilder.UserPayload.loginUserPayload;
import static payloadBuilder.UserPayload.registerUserPayload;

public class UserRequestBuilder {

    static String registerUserId;
    static String token;

    public static Response userRegistrationRequest(String firstname, String lastname, String email, String password, String groupId) {

        String apiPath = "/APIDEV/register";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json") // other way if it's only for JSON type : contentType(ContentType.JSON)
                .body(registerUserPayload(firstname, lastname, email, password, groupId))
                .when()
                .post()
                .then()
                .extract().response();

        registerUserId= response.jsonPath().getString("data.id");

        return response;

    }

    public static Response userLoginRequest(String email, String password) {

        String apiPath = "/APIDEV/login";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json")
                .body(loginUserPayload(email, password))
                .when()
                .post()
                .then()
                .extract().response();

        token = response.jsonPath().getString("data.token");

        return response;

    }

}
