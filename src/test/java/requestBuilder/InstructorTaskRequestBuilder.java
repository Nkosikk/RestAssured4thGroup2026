package requestBuilder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;
import static test.InstructorTaskTests.taskId;


public class InstructorTaskRequestBuilder {

    public static Response createTask(String title, String description, String groupId, String priority, String dueDate) {
        String apiPath = "/APIDEV/instructor/tasks";
        Map<String, Object> payload = new HashMap<>();
                payload.put("title",title);
                payload.put("description",description);
                payload.put("groupId",groupId);
                payload.put("priority",priority);
                payload.put("dueDate",dueDate);
                payload.put("studentIds",new ArrayList<>());
                payload.put("documents",new ArrayList<>());


        return given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + AdminRequestBuilder.adminToken)
                .body(payload)
                .log().all()
                .when()
                .post()
                .then()
                .extract()
                .response();

    }


    public static Response updateTask(String taskId,String title, String description, String groupId, String priority, String dueDate) {
        String apiPath = "/APIDEV/instructor/tasks/" + taskId ;
        Map<String, Object> payload = new HashMap<>();
        payload.put("title",title);
        payload.put("description",description);
        payload.put("groupId",groupId);
        payload.put("priority",priority);
        payload.put("dueDate",dueDate);
        payload.put("studentIds",new ArrayList<>());
        payload.put("documents",new ArrayList<>());


        return given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + AdminRequestBuilder.adminToken)
                .body(payload)
                .log().all()
                .when()
                .put()
                .then()
                .extract()
                .response();

    }

    public static Response getGetTaskCompletions(String taskId){
        String apiPath = "/APIDEV/instructor/tasks/" + taskId;


        return given()
                .baseUri(BASE_URL)
                .basePath(apiPath + "/completions")
                .contentType(ContentType.JSON)
                .header("Authorization",  "Bearer " + AdminRequestBuilder.adminToken)
                .log().all()
                .when()
                .get()
                .then()
                .extract()
                .response();

    }

    public static Response deleteTask(String taskId){
        String apiPath = "/APIDEV/instructor/tasks/{taskId}";


        return  given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .pathParam("taskId", taskId)
                .auth()
                .oauth2(AdminRequestBuilder.adminToken)
                .contentType(ContentType.JSON)
                .when()
                .delete()
                .then()
                .extract()
                .response();


    }


    public static Response getInstructorTask(boolean all, String groupId, int limit, int offset){
        String apiPath = "/APIDEV/instructor/tasks";

        System.out.println("GET TOKEN = "  + AdminRequestBuilder.adminToken);


        return  given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .queryParam("all", all)
                .queryParam("groupId", groupId)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .auth()
                .oauth2(AdminRequestBuilder.adminToken)
                .contentType(ContentType.JSON)
                .when()
                .get();


    }








    }



















