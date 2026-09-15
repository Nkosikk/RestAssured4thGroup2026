package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;



import static commons.Routes.BASE_URL;
import static requestBuilder.UserRequestBuilder.registerUserId;

public class AdminRequestBuilder {

    public static String adminToken;
    public static String adminEmail = "demonslayer@gmail.com";
    public static String adminPassword = "Hashira@2026";
    public static Response response;



    public static Response adminLoginRequest() {

        response = UserRequestBuilder.userLoginRequest(adminEmail, adminPassword);
        adminToken = response.jsonPath().getString("data.token");


        return response;

    }


    public static Response approveUserRequest() {

        String apiPath = "/APIDEV/admin/users/" + registerUserId+ "/approve";

        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json")
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .put();






    }

}
