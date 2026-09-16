package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;


import static commons.Routes.*;
import static requestBuilder.UserRequestBuilder.registerUserId;

public class AdminRequestBuilder {

    public static String adminToken;
    public static String adminEmail = "demonslayer@gmail.com";
    public static String adminPassword = "Hashira@2026";


    public static Response adminLoginRequest() {

        Response response = UserRequestBuilder.userLoginRequest(adminEmail, adminPassword);
        adminToken = response.jsonPath().getString("data.token");


        return response;

    }


    public static Response approveUserRequest() {

        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(APPROVE_USER)
                .contentType("application/json")
                .header("Authorization", "Bearer " + adminToken)
                .pathParam("id", registerUserId)
                .when()
                .put();

    }

}
