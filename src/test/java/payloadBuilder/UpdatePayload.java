package payloadBuilder;

import java.util.HashMap;
import java.util.Map;

public class UpdatePayload {

    public static Map<String, Object> updateTaskPayload(
            String title,
            String description,
            String priority,
            String dueDate) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("title", title);
        payload.put("description", description);
        payload.put("priority", priority);
        payload.put("dueDate", dueDate);

        return payload;
    }








}
