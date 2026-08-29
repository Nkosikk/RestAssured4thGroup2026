package commons;

public class Routes {

    public static final String BASE_URL = "https://ndosiautomation.co.za";
    public static final String INSTRUCTOR_TASKS = BASE_URL + "/APIDEV/instructor/tasks";
    public static final String CREATE_TASK = INSTRUCTOR_TASKS;
    public static final String UPDATE_TASK = INSTRUCTOR_TASKS + "/{taskId}";
    public static final String DELETE_TASK = INSTRUCTOR_TASKS + "/{taskId}";
    public static final String TASK_COMPLETION = INSTRUCTOR_TASKS + "/{taskId}/completions";


    public static final String DB_URL = "jdbc:mysql://102.222.124.22:3306/ndosian6b8b7_teaching";
    public static final String DB_USERNAME = "ndosian6b8b7_teaching";
    public static final String DB_PASSWORD = "^{SF0a=#~[~p)@l1";
    public static final String JSON_SCHEMA_PATH = "src/test/resources/schemas/";

}
