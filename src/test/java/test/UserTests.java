package test;

import com.github.javafaker.Faker;
import commons.Routes;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.UserRequestBuilder;
import utils.DBConnection;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static utils.DBConnection.testConnection;

public class UserTests {

    //declaring variables to be used in the tests
    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static String groupId;
    static String invalidUsername = "InvalidUser";
    static String invalidPassword = "InvalidPass";
    static String taskTitle;
    static String taskDescription;
    static String priority;
    static String dueDate;
    static String updatedTaskTitle;
    static String updatedTaskDescription;


    static Faker faker = new Faker();

    //initializing the variables with random data before running the tests
    @BeforeClass
    public static void setupData() {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = "Group4" + System.currentTimeMillis() + "@hotmail.com"; //unique email for each test run
        password = "7654321!";
        groupId = "cdc7d817-b518-4d0c-a9fa-b8ae0585cd64";
        //Setup for Create Task
        taskTitle = "Task - " + faker.job().title();
        taskDescription = faker.lorem().sentence();
        priority = "medium";
        dueDate = "2026-08-30T10:28:45.450Z";
        updatedTaskTitle = "Updated Task " + System.currentTimeMillis();
        updatedTaskDescription = faker.lorem().sentence();

    }

    @Test

    public void verifyConnection() {

        testConnection();
    }

    @Test
    public void testUserRegistration() throws SQLException {
        Response response = UserRequestBuilder.userRegistrationRequest(firstName, lastName, email, password, groupId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 201);
        DBConnection.insertUser(email, password); //
        System.out.println("About to insert: " + email + " / " + password);
        DBConnection.insertUser(email, password);
        System.out.println("Insert completed");
    }

    @Test
    public void testAdminLogin() {
        Response response = AdminRequestBuilder.adminLogin();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

//    @Test(dependsOnMethods = {"testUserRegistration", "testAdminLogin"})
//    public void testUserApproval() {
//        AdminRequestBuilder.UserApproval()
//                .then()
//                .log().all()
//                .assertThat()
//                .statusCode(200)
//                .body("success", equalTo(true))
//                .body("data.approvalStatus", equalTo("approved"));
//    }

    @Test(dependsOnMethods = {"testUserRegistration", "testAdminLogin"})

    public void testUserApproval() {
        Response response = AdminRequestBuilder.UserApproval();
        response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.approvalStatus", equalTo("approved"));
        String schemaPath = Paths.get(
                        Routes.JSON_SCHEMA_PATH + "UserApprovalSchema.json")
                .toAbsolutePath()
                .toString();
        response.then().assertThat()
                .body(matchesJsonSchema(
                        Paths.get(schemaPath).toFile()));

    }


    @Test(dependsOnMethods = {"testUserApproval"})
    public void testRegisteredUserLogin() throws SQLException {
        DBConnection.getLoginDetails(email); // Retrieve the login details from the database
        // UserRequestBuilder.userLogin(email, password) - Using the registered email and password from the setupData method
        UserRequestBuilder.userLogin(DBConnection.emailFromDB, DBConnection.passwordFromDB)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.token", notNullValue());
        System.out.println("Generated Email: " + email);
    }

    @Test(dependsOnMethods = {"testRegisteredUserLogin"})
    public void createTaskTest() {

        Response response = UserRequestBuilder.createTask(
                taskTitle,
                taskDescription,
                groupId,
                priority,
                dueDate,
                UserRequestBuilder.registeredUserId
        );

        response.then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("success", equalTo(true))
                .body("data.title", equalTo(taskTitle));

        Assert.assertNotNull(
                response.jsonPath().getString("data.id"));

        System.out.println(
                "Task ID: "
                        + response.jsonPath().getString("data.id"));
    }

    @Test(dependsOnMethods = {"createTaskTest"})
    public void getTaskTest() {

        Response response =
                UserRequestBuilder.getTask(
                        UserRequestBuilder.createdTaskId);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data.tasks[0].Id",
                        equalTo(UserRequestBuilder.createdTaskId))
                .body("data.tasks[0].Title",
                        equalTo(taskTitle));


    }

    @Test(dependsOnMethods = {"createTaskTest"})
    public void updateTaskTest() {

        Response response =UserRequestBuilder.updateTask( UserRequestBuilder.createdTaskId,
                        updatedTaskTitle,
                        updatedTaskDescription,
                        priority,
                        dueDate);

        response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));

        System.out.println(
                "Updated Task ID : "
                        + UserRequestBuilder.createdTaskId);
    }

    @Test(dependsOnMethods = {"updateTaskTest"})
    public void completeTaskTest() {

        Response response =
                UserRequestBuilder.completeTask(
                        UserRequestBuilder.createdTaskId);

        response.then()
                .log().all()
                .statusCode(200);

        System.out.println(
                "Completed Task ID : "
                        + UserRequestBuilder.createdTaskId);
    }

    @Test(dependsOnMethods = {"completeTaskTest"})
    public void deleteTaskTest() {

        Response response =
                UserRequestBuilder.deleteTask(
                        UserRequestBuilder.createdTaskId);

        response.then()
                .log().all()
                .statusCode(200);

        System.out.println(
                "Deleted Task ID : "
                        + UserRequestBuilder.createdTaskId);
    }


    @Test
    public void InvalidLoginTest() {
        //UserRequestBuilder.userLogin(invalidUsername, invalidPassword)
        UserRequestBuilder.userLogin("invalidUsername", "invalidPassword")
                .then()
                .log().all()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("error_code", equalTo("INVALID_CREDENTIALS"));
    }

//    @Test(dependsOnMethods = {"testUserRegistration", "testAdminLogin"})
//    public void userApprovalSchemaValidationTest() {
//        //The issue her was calling that we did not add test AdminLogin before calling the UserApproval API,
//        // so the token was not set and the request was failing. Now we have added the dependency on testAdminLogin
//        // to ensure that the admin login is successful before calling the UserApproval API.
//        Response response = AdminRequestBuilder.UserApproval();
//
//        try {
//            String savedSchema = Files.readString(
//                    Paths.get(Routes.JSON_SCHEMA_PATH + "UserApprovalSchema.json"));
//            System.out.println("Loaded JSON schema:\n" + savedSchema);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String schemaPath = Paths.get(Routes.JSON_SCHEMA_PATH + "UserApprovalSchema.json").toAbsolutePath().toString();
//        response.then().assertThat().body(matchesJsonSchema(Paths.get(schemaPath).toFile()));
//        response.then().log().all();
//    }


    @Test //(dependsOnMethods = {"testUserRegistration"})
    public void schemaValidationTest() {

        String schemaEmail =

                "Group4" + System.currentTimeMillis() + "@hotmail.com";

        Response response = UserRequestBuilder.userRegistrationRequest(firstName, lastName, schemaEmail, password, groupId);

        try {
            String savedSchema = Files.readString(
                    Paths.get(Routes.JSON_SCHEMA_PATH + "UserRegistrationSchema.json"));
            System.out.println("Loaded JSON schema:\n" + savedSchema);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String schemaPath = Paths.get(Routes.JSON_SCHEMA_PATH + "UserRegistrationSchema.json").toAbsolutePath().toString();
        response.then().assertThat().body(matchesJsonSchema(Paths.get(schemaPath).toFile()));
        System.out.println("Schema email: " + schemaEmail);
    }

    //Fix the UserApproval Schema test to validate the response schema for the UserApproval API

    @Test
    public void getCourseTest() {

        AdminRequestBuilder.getCourse("beginner")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }
}
