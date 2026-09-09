package Requestbuilder;

import Payloadbuilder.TestimonialsPayloads;
import io.restassured.response.Response;

import static Commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class TestimonialsRequestBuilder {

    static String loginToken;

    public static Response loginRequest(String email, String password) {
        String apiPath = "/APIDEV/login";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json")
                .body(TestimonialsPayloads.LoginPayload(email, password))
                .when()
                .post()
                .then()
                .extract().response();

        loginToken = response.jsonPath().getString("data.token");
        return response;
    }

    public static Response createTestimonialRequest(String title, String content, int rating, boolean isPublic){

        String apiPath = "/APIDEV/testimonials";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json")
                .header("Authorization", "Bearer " + loginToken)
                .body(TestimonialsPayloads.createTestimonialPayload(title, content, rating, isPublic))
                .when()
                .post()
                .then()
                .extract().response();

        return response;
    }

    public static Response updateTestimonialRequest(String testimonialId, String title, String content, int rating){

        String apiPath = "/APIDEV/testimonials/" + testimonialId;
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json")
                .header("Authorization", "Bearer " + loginToken)
                .body(TestimonialsPayloads.upDateTestimonialPayload(title, content, rating))
                .when()
                .put()
                .then()
                .extract().response();

        return response;
    }


}
