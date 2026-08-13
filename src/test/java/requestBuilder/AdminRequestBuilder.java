package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static commons.Routes.BASE_URL;

public class AdminRequestBuilder {

    //add admin login method to get the token and store it in a static variable for later use in other requests
    static String adminToken;

    public static Response adminLogin() {
        Response response = UserRequestBuilder.userLogin("titi@gmail.com", "tlou@97LT");
        adminToken = response.jsonPath().getString("data.token");
        System.out.println("Admin Token: " + adminToken);
        return response;
    }


    public static Response UserApproval(){
        String apiPath = "/APIDEV/admin/users/"+ UserRequestBuilder.registeredUserId+"/approve";
        return RestAssured.given()
                    .baseUri(BASE_URL)
                    .basePath(apiPath)
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + AdminRequestBuilder.adminToken)
                    .log().all()
                .when()
                    .put()
                .then()
                    .extract().response();

    }

}
