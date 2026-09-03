package requestBuilder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloadBuilder.UserPayload;

import static commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class UserRequestBuilder {

    static String registeredUserId;
    static String userToken;

    public static Response userRegistrationRequest(String firstName, String lastName, String email, String password, String groupId) {

        String apiPath = "/APIDEV/register";
        Response response = given()
                    .baseUri(BASE_URL)
                    .basePath(apiPath)
                    .contentType(ContentType.JSON)
                    .body(UserPayload.userRegistrationPayload(firstName, lastName, email, password, groupId))
                .when() //optional, can be removed
                    .post()
                .then()
                    .extract().response();

        registeredUserId = response.jsonPath().getString("data.id");
        return response;
    }

    public static Response userLogin(String email, String password) {

        String apiPath = "APIDEV/login";
        Response response = given()
                    .baseUri(BASE_URL)
                    .basePath(apiPath)
                    .header("Content-Type", "application/json") // similar to contentType(ContentType.JSON), just specific on the content type header
                    .body(UserPayload.loginPayload(email, password))
                .when()
                    .post()
                .then()
                    .extract().response();

        userToken = response.jsonPath().getString("data.token");
        return response;
    }


        public static Response getGroupStudents(String groupId, String userToken) {
        String apiPath = "/APIDEV/groups/" + groupId + "/students";
        return given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get();
    }
}
