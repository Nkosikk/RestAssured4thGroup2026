
package requestBuilder;
import commons.Routes;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloadBuilder.UserPayload;
import utils.TestData;

import static io.restassured.RestAssured.given;
import static utils.TestData.taskId;

public class TaskRequests {
        public static Response getInstructorTasks(
            String token,
            boolean all,
            String groupId,
            int limit,
            int offset) {

        return given()
                .header("Authorization", "Bearer " + token)
                .queryParam("all", all)
                .queryParam("groupId", groupId)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .when()
                .get(Routes.GET_INSTRUCTOR_TASKS);
    }
    public static Response createTask(
            String token,
            String groupId) {

        return given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(UserPayload.TaskPayload.createTask(groupId))
                .when()
                .post(Routes.CREATE_TASK);
    }
    public static Response updateTask(
            String token,
            String taskId) {

        return given()
                .header("Authorization", "Bearer " + token)
                .pathParam("taskId", taskId)
                .when()
                .put(Routes.UPDATE_TASK);
    }
    public static Response deleteTask(
            String token,
            String taskId) {

        return given()
                .header("Authorization", "Bearer " + token)
                .pathParam("taskId", taskId)
                .when()
                .delete(Routes.DELETE_TASK);
    }
    public static Response getTaskCompletion(
            String token,
            String taskId) {

        return given()
                .header("Authorization", "Bearer " + token)
                .pathParam("taskId", taskId)
                .queryParam("limit", 5)
                .when()
                .get(Routes.GET_TASK_COMPLETION);
    }
}