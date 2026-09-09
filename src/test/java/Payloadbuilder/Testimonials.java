package Payloadbuilder;

public class Testimonials {

    public static String loginPayload(String username, String password) {
        return "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}";
    }

}
