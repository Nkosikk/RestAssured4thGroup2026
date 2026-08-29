package payloadBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructorTaskPayload {

    public static Map<String, Object> createTaskPayload(
            String title,
            String description,
            String groupId,
            String priority,
            String dueDate){
        Map<String, Object> payload = new HashMap<>();

        payload.put("title",title);
        payload.put("description",description);
        payload.put("groupId",groupId);
        payload.put("priority",priority);
        payload.put("dueDate",dueDate);
        payload.put("studentIds",new ArrayList<>());
        payload.put("documents",new ArrayList<>());

        return  payload;

    }

}
