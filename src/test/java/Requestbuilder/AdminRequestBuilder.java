package Requestbuilder;

import Payloadbuilder.TestimonialsPayloads;
import io.restassured.response.Response;
import org.apache.http.client.methods.RequestBuilder;

import static Commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class AdminRequestBuilder {

    public static Response approveOrRejectTestimonialRequest(String testimonialId, String status, String notes){

        String apiPath = "/APIDEV/admin/testimonials/" + testimonialId + "/status";
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
}
