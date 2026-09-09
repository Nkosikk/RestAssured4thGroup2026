package Requestbuilder;

import Payloadbuilder.TestimonialsPayloads;
import io.restassured.response.Response;
import org.apache.http.client.methods.RequestBuilder;

import static Commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class AdminRequestBuilder {

    public static Response approveOrRejectTestimonialRequest(String testimonialId, String status, String notes){

        String apiPath = "/APIDEV/admin/testimonials/" + testimonialId;
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json")
                .header("Authorization", "Bearer " + TestimonialsRequestBuilder.loginToken)
                .body(TestimonialsPayloads.approveOrRejectTestimonialPayload(status, notes))
                .when()
                .put()
                .then()
                .extract().response();

        return response;
    }

    public static Response getAllTestimonialsRequest(String status, String isPublic) {
        String apiPath = "/APIDEV/admin/testimonials?status=pending";

        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType("application/json")
                .header("Authorization", "Bearer " + TestimonialsRequestBuilder.loginToken)
                .queryParam("status", status)
                .queryParam("isPublic", isPublic)
                .when()
                .get()
                .then()
                .extract().response();

        return response;
    }
}
