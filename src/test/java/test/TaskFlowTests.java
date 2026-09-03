package test;

import commons.Routes;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloadBuilder.UserPayload;
import requestBuilder.GroupRequestsBuilder;
import requestBuilder.TaskRequests;
import requestBuilder.UserRequestBuilder;
import utils.TestData;

import static io.restassured.RestAssured.given;

public class TaskFlowTests {

    @Test
    public void loginUser() {

        Response response =
                UserRequestBuilder.userLogin(
                        "jugzin@gmail.com",
                        "Cjsin@123");

        response.then().statusCode(200);

        TestData.userToken =
                response.jsonPath().getString("data.token");

        Assert.assertNotNull(TestData.userToken);
    }

    @Test(dependsOnMethods = "loginUser")
    public void getAllGroups() {

        Response response =
                GroupRequestsBuilder.getAllGroups(TestData.userToken,
                        10,
                        0,
                        true);

        TestData.groupId = response.jsonPath().getString("data.groups[0].Id");

        System.out.println("Group ID = " + TestData.groupId);
    }
    @Test (dependsOnMethods = "deleteTask")
    public void getInstructorTasks() {

        Response response =
                TaskRequests.getInstructorTasks(TestData.userToken,
                        true,
                        TestData.groupId,
                        10,
                        0);

        response.prettyPrint();

        //
        TestData.instructorTaskId = response.jsonPath()
                .getString("data.tasks[0].Id");

        System.out.println("Task ID = " + TestData.instructorTaskId);
    }
    @Test(dependsOnMethods = "getAllGroups")
    public void createTask() {

        Response response =
                TaskRequests.createTask(
                        TestData.userToken,
                        TestData.groupId);

        response.prettyPrint();

        response.then().statusCode(201);

        TestData.createdTaskId =
                response.jsonPath().getString("data.id");

        Assert.assertNotNull(TestData.createdTaskId);

        System.out.println(
                "Created Task ID = "
                        + TestData.createdTaskId);
    }
    @Test(dependsOnMethods = "getInstructorTasks")
    public void updateTask() {

        System.out.println(
                "Task ID to update = "
                        + TestData.instructorTaskId);

        Response response =
                TaskRequests.updateTask(
                        TestData.userToken,
                        TestData.instructorTaskId);

        response.prettyPrint();

        response.then().statusCode(200);
    }


    @Test(dependsOnMethods = "createTask")
    public void getTaskCompletion() {

        Response response =
                TaskRequests.getTaskCompletion(TestData.userToken, TestData.createdTaskId);

        response.prettyPrint();

        response.then().statusCode(200);
    }
    @Test(dependsOnMethods = "getTaskCompletion")
    public void deleteTask() {

        System.out.println(
                "Task ID to delete = "
                        + TestData.createdTaskId);

        Response response =
                TaskRequests.deleteTask(
                        TestData.userToken,
                        TestData.createdTaskId);

        response.prettyPrint();

        response.then().statusCode(200);
    }

}

