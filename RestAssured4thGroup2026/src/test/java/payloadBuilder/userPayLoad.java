package payloadBuilder;

import org.json.simple.JSONObject;

public class userPayLoad {

    public static JSONObject userRegistrationPayload(String firstname, String lastName, String email, String password,String groupId){
        //declaring a Json object instance
        JSONObject registerUser = new JSONObject();

        //Adding key value pairs in the registerUser object
        registerUser.put("firstName", firstname);
        registerUser.put("lastName", lastName);
        registerUser.put("email", email);
        registerUser.put("password", password);
        registerUser.put("confirmPassword", password);
        registerUser.put("groupId", groupId);

        return registerUser;

    }

    public static JSONObject loginPayload(String email, String password){
        JSONObject loginUser = new JSONObject(); //Instantiate loginUser object of type JSONObject

        //Putting the key pair values in the loginUser object
        loginUser.put("email", email);
        loginUser.put("password", password);

        return loginUser;
    }
}
