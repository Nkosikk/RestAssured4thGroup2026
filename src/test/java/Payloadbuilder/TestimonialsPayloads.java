package Payloadbuilder;


import org.json.JSONObject;

public class TestimonialsPayloads {

    public static JSONObject LoginPayload(String email, String password) {
        JSONObject payload = new JSONObject();
        payload.put("username", email);
        payload.put("password", password);
        return payload;
    }

    public static JSONObject createTestimonialPayload(String title, String content, int rating, boolean isPublic) {
        JSONObject payload = new JSONObject();
        payload.put("title", title);
        payload.put("content", content);
        payload.put("rating", rating);
        payload.put("isPublic", isPublic);
        return payload;
    }

    public static JSONObject upDateTestimonialPayload(String title, String content, int rating) {
        JSONObject payload = new JSONObject();
        payload.put("title", title);
        payload.put("content", content);
        payload.put("rating", rating);
        return payload;
    }

    public static JSONObject approveOrRejectTestimonialPayload(String status, String notes) {
        JSONObject payload = new JSONObject();
        payload.put("status", status);
        payload.put("notes", notes);
        return payload;
    }
}
