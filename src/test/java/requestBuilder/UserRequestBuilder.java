package requestBuilder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


import java.util.HashMap;
import java.util.Map;

import static commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class UserRequestBuilder {


    public static Response userLogin(String email, String password) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);

        String apiPath = "APIDEV/login";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("Content-Type", "application/json") // similar to contentType(ContentType.JSON), just specific on the content type header
                .body(payload)
                .when()
                .post()
                .then()
                .extract()
                .response();

        return response;
    }


}
