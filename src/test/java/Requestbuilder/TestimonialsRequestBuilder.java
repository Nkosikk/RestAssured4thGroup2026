package Requestbuilder;

import Payloadbuilder.TestimonialsPayloads;
import io.restassured.response.Response;

import static Commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class TestimonialsRequestBuilder {

    public static String loginToken;
    static String testimonialId;

    public static Response loginRequest(String email, String password) {

        String apiPath = "/APIDEV/login";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("Accept", "application/json")
                .contentType("application/json")
                .body(TestimonialsPayloads.LoginPayload(email,password))
                .log().all()
                .when()
                .post()
                .then()
                .extract().response();


            loginToken = response.jsonPath().getString("data.token");

        return response;

    }

    public static Response createTestimonialRequest(String loginToken){

        String apiPath = "/APIDEV/testimonials";

        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("Accept", "application/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + TestimonialsRequestBuilder.loginToken)
                .body(TestimonialsPayloads.createTestimonialPayload("Testimonial Title", "Testimonial Content", 5, true))
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

    public static Response publicTestimonialRequest(){

        String apiPath = "/APIDEV/testimonials?limit=50&offset=0";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json")
                .when()
                .get()
                .then()
                .extract().response();

        return response;
    }

    public static Response getMyTestimonialsRequest(){

        String apiPath = "/APIDEV/my-testimonials";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("Authorization", "Bearer " + loginToken)
                .contentType("application/json")
                .when()
                .get()
                .then()
                .extract().response();


        return response;
    }

    public static Response deleteTestimonialRequest(){

        String apiPath = "/APIDEV/testimonials/" + testimonialId;
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json")
                .header("Authorization", "Bearer " + loginToken)
                .when()
                .delete()
                .then()
                .extract().response();

        return response;
    }

}
