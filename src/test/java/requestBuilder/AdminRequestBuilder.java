package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class AdminRequestBuilder {

    //add admin login method to get the token and store it in a static variable for later use in other requests
    public static String adminToken;
    public static String adminUsername = "titi@gmail.com";
    public static String adminPassword = "tlou@97LT";


    public static Response adminLogin() {
        Response response = UserRequestBuilder.userLogin(adminUsername, adminPassword);
        adminToken = response.jsonPath().getString("data.token");
        System.out.println("Admin Token: " + adminToken);
        return response;
    }



    }







