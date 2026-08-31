package requestBuilder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloadBuilder.TaskCompleted;
import payloadBuilder.TaskPayload;
import payloadBuilder.UpdatePayload;
import payloadBuilder.UserPayload;

import static commons.Routes.BASE_URL;
import static io.restassured.RestAssured.given;

public class UserRequestBuilder {

    public static String registeredUserId;
    public static String userToken;
    public static String createdTaskId;
    static String updatedTaskTitle;
    static String updatedTaskDescription;

    public static Response userRegistrationRequest(String firstName, String lastName, String email, String password, String groupId) {

        String apiPath = "/APIDEV/register";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType(ContentType.JSON)
                .body(UserPayload.userRegistrationPayload(firstName, lastName, email, password, groupId))
                .when() //optional, can be removed
                .post()
                .then()
                .extract().response();

        response.prettyPrint();

        registeredUserId = response.jsonPath().getString("data.id");

        System.out.println("Registered User ID: " + registeredUserId);

        return response;

    }

    public static Response userLogin(String email, String password) {

        String apiPath = "APIDEV/login";
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("Content-Type", "application/json") // similar to contentType(ContentType.JSON), just specific on the content type header
                .body(UserPayload.loginPayload(email, password))
                .when()
                .post()
                .then()
                .extract().response();

        userToken = response.jsonPath().getString("data.token");
        return response;
    }

    public static Response createTask(String title,
                                      String description,
                                      String groupId,
                                      String priority,
                                      String dueDate,
                                      String studentId) {

        String apiPath = "/APIDEV/instructor/tasks";

        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + AdminRequestBuilder.adminToken)
                .body(TaskPayload.createTaskPayload(
                        title,
                        description,
                        groupId,
                        priority,
                        dueDate,
                        studentId))
                .when()
                .post()
                .then()
                .extract()
                .response();

        response.prettyPrint();

        createdTaskId = response.jsonPath().getString("data.id");

        System.out.println("Created Task ID: " + createdTaskId);

        return response;
    }
    public static Response getTask(String taskId) {

        String apiPath = "/APIDEV/instructor/tasks";

        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + AdminRequestBuilder.adminToken)
                .when()
                .get(apiPath)
                .then()
                .extract()
                .response();

        response.prettyPrint();

        return response;
    }

    public static Response updateTask(String taskId,
                                      String title,
                                      String description,
                                      String priority,
                                      String dueDate) {

        String apiPath = "/APIDEV/instructor/tasks/" + taskId;

        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization",
                        "Bearer " + AdminRequestBuilder.adminToken)
                .body(UpdatePayload.updateTaskPayload(
                        title,
                        description,
                        priority,
                        dueDate))
                .when()
                .put(apiPath)
                .then()
                .extract()
                .response();

        response.prettyPrint();

        return response;
    }

    public static Response completeTask(String taskId) {

        String apiPath = "/APIDEV/instructor/tasks/" + taskId + "/completions";

        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization",
                        "Bearer " + AdminRequestBuilder.adminToken)
                .when()
                .get(apiPath)
                .then()
                .extract()
                .response();

        response.prettyPrint();

        return response;
    }

    public static Response deleteTask(String taskId) {

        String apiPath = "/APIDEV/instructor/tasks/" + taskId;

        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization",
                        "Bearer " + AdminRequestBuilder.adminToken)
                .when()
                .delete(apiPath)
                .then()
                .extract()
                .response();

        response.prettyPrint();

        return response;
    }





}
