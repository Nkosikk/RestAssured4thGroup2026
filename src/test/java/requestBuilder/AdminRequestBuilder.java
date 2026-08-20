package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class AdminRequestBuilder {

    //add admin login method to get the token and store it in a static variable
    public static String adminToken;
    public static String adminUsername = "admin@gmail.com";
    public static String adminPassword = "@12345678";

    public static Response adminLogin() {
        Response response = UserRequestBuilder.userLogin(adminUsername, adminPassword);
        adminToken = response.jsonPath().getString("data.token");
        System.out.println("Admin Token: " + adminToken);
        return response;
    }


    public static Response UserApproval(){
        String apiPath = "/APIDEV/admin/users/"+UserRequestBuilder.registeredUserId+"/approve";
        return given()
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

    public static Response getCourse(String level){
        String apiPath = "/APIDEV/courses";
        return given()
                    .baseUri(BASE_URL)
                    .basePath(apiPath)
                    .contentType(ContentType.JSON)
                    .queryParam("level", level) //specifying query parameter for filtering courses by level
                    .log().all()
                .when()
                    .get()
                .then()
                    .extract().response();
    }

}
