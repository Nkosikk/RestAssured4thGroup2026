package payloadBuilder;

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

////   --commented out the example payload method for now, can be used for practice purposes later
//  @Test
//    public void payloadExample() {
//        JSONObject examplePayload = new JSONObject();
//        examplePayload.put("key1", "value1");
//        examplePayload.put("key2", "value2");
//
//        System.out.println(examplePayload.toJSONString());
//    }


    public static JSONObject loginPayload(String email, String password) {
        JSONObject loginUser = new JSONObject(); //instantiate loginUser object of type JSONObject
        loginUser.put("email", email); //putting key-value pairs in the loginUser object
        loginUser.put("password", password);

        return loginUser;
    }


}
