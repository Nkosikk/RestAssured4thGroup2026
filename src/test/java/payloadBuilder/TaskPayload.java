package payloadBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TaskPayload {


    public static Map<String, Object> createTaskPayload(

            String title,

            String description,

            String groupId,

            String priority,

            String dueDate,

            String studentId) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("title", title);

        payload.put("description", description);

        payload.put("groupId", groupId);

        payload.put("priority", priority);

        payload.put("dueDate", dueDate);

        payload.put("studentIds",

                Collections.singletonList(studentId));

        payload.put("studentId", studentId);

        Map<String, String> document = new HashMap<>();

        document.put("name", "QA Task");

        payload.put("documents",

                Collections.singletonList(document));

        return payload;

    }


}
