package payLoadBuild;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class UserPayload {

    public static JSONObject userRegistrationPayload(String firstName, String lastName, String email, String password, String groupId) {
        JSONObject registerUser = new JSONObject();
        registerUser.put("firstName", firstName); //adding key-value pairs in the registerUser object
        registerUser.put("lastName", lastName);
        registerUser.put("email", email);
        registerUser.put("password", password);
        registerUser.put("confirmPassword", password);
        registerUser.put("groupId", groupId);

        return registerUser;
    }

    public static JSONObject loginPayload(String email, String password) {
        JSONObject loginUser = new JSONObject(); //instantiate loginUser object of type JSONObject
        loginUser.put("email", email); //putting key-value pairs in the loginUser object
        loginUser.put("password", password);

        return loginUser;
    }


}