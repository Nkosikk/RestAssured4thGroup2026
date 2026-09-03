package commons;

public class Routes {

    public static final String BASE_URL = "https://ndosiautomation.co.za";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/ndosiautomation";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "password";
    public static final String REGISTER_API = "/APIDEV/register";
    public static final String LOGIN_API = "/APIDEV/login";
    public static final String USER_APPROVAL_API = "/APIDEV/admin/users/{userId}/approve";
    public static final String USER_REJECTION_API = "/APIDEV/admin/users/{userId}/reject";
    public static final String USER_DETAILS_API = "/APIDEV/admin/users/{userId}";
    public static final String USER_LIST_API = "/APIDEV/admin/users";
    public static final String LOGIN = BASE_URL + "/APIDEV//auth/login";
    public static final String GET_ALL_GROUPS = BASE_URL + "/APIDEV/instructor/groups";
    public static final String CREATE_TASK = BASE_URL + "/APIDEV/instructor/tasks";
    public static final String GET_TASK_COMPLETION = BASE_URL + "/APIDEV/instructor/tasks/{taskId}/completions";
    public static final String GET_INSTRUCTOR_TASKS = BASE_URL + "/APIDEV/instructor/tasks";
    public static final String UPDATE_TASK = BASE_URL + "/APIDEV/instructor/tasks/{taskId}";
    public static final String DELETE_TASK = BASE_URL + "/APIDEV/instructor/tasks/{taskId}";
    }

