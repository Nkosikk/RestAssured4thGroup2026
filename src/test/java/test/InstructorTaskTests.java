package test;



import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.InstructorTaskRequestBuilder;

import static org.testng.Assert.assertEquals;
import static requestBuilder.InstructorTaskRequestBuilder.createTask;
import static requestBuilder.InstructorTaskRequestBuilder.updateTask;



public class InstructorTaskTests {

    public static String taskId;
    public static String adminToken;

    @Test
    public void testAdminLogin() {
        Response response = AdminRequestBuilder.adminLogin();

        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
        adminToken = response.jsonPath().getString("token");
        System.out.println("Admin Token" + adminToken);
    }

    @Test(dependsOnMethods = "testAdminLogin")
    public void createTaskTest() {

        Response response = createTask(
                "Lydia API Testing Task",
                "Testing the instructor task creation overflow",
                "f552e054-8b24-41c2-a0ce-24018a205783",
                "medium",
                "2026-09-04"
        );

        response.then().log().all();

        assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        assertEquals(response.jsonPath().getString("message"), "Task created successfully");

        taskId = response.jsonPath().getString("data.id");
        System.out.println("Created Task ID: " + taskId);


    }

    @Test(dependsOnMethods = "createTaskTest")
    public void updateTaskTest() {

        Response response = updateTask(
                taskId,
                "Lydia API Testing Updated Task",
                "Testing the instructor task update overflow",
                "f552e054-8b24-41c2-a0ce-24018a205783",
                "high",
                "2026-09-04"
        );

        response.then().log().all();

        assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        assertEquals(response.jsonPath().getString("message"), "Task updated successfully");

        taskId = response.jsonPath().getString("data.id");
        System.out.println("Updated Task ID: " + taskId);
    }


    @Test(dependsOnMethods = "updateTaskTest")
    public void getCompletionsTest() {

        Response response = InstructorTaskRequestBuilder.getGetTaskCompletions(taskId);


        response.then().log().all();

        assertEquals(response.getStatusCode(), 200);

        Assert.assertTrue(response.jsonPath().getBoolean("success"));

        Assert.assertEquals(response.jsonPath().getString("message"), "Task completions retrieved successfully");

        Assert.assertEquals(response.jsonPath().getString("data.task.id"), taskId);

        System.out.println("Task completions retrived for: " + taskId);

    }


    @Test(dependsOnMethods = "getCompletionsTest")
    public void deleteTaskTest() {

        Response response = InstructorTaskRequestBuilder.deleteTask(taskId);

        response.then().log().all();

        assertEquals(response.getStatusCode(), 200);

        Assert.assertTrue(response.jsonPath().getBoolean("success"));

        System.out.println("Task deleted successfully: " + taskId);

    }

    @Test(dependsOnMethods = "deleteTaskTest")
    public void getDeletedTaskCompletionsTest() {

        Response response = InstructorTaskRequestBuilder.getGetTaskCompletions(taskId);
        //Negative assertion
        response.then().log().all();

        assertEquals(response.getStatusCode(), 404);

        Assert.assertFalse(response.jsonPath().getBoolean("success"));

        assertEquals(response.jsonPath().getString("error_code"), "NOT_FOUND");

        System.out.println("Negative test passed: deleted task cannot be accessed " + taskId);

    }

    @Test(dependsOnMethods = "testAdminLogin")
    public void getInstructorTest() {

        Response response = InstructorTaskRequestBuilder.getInstructorTask(true, "f552e054-8b24-41c2-a0ce-24018a205783", 100, 0);

        response.then().log().all();

        assertEquals(response.getStatusCode(), 200);

        Assert.assertTrue(response.jsonPath().getBoolean("success"));

        System.out.println("Instructor tasks retrieved successfully");

    }





}












