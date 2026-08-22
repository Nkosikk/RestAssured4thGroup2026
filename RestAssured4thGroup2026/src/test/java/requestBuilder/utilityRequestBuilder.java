package requestBuilder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class utilityRequestBuilder {

    public static Response utility(){
    String apiPath = "/APIDEV/";
        return given()
            .baseUri(BASE_URL)
            .basePath(apiPath)
            .contentType(ContentType.JSON)
            .get()
            .then()
            .extract().response();
    }
}
