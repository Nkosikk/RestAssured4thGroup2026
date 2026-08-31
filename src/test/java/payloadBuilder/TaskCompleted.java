package payloadBuilder;

import java.util.HashMap;
import java.util.Map;

public class TaskCompleted {

        public static Map<String, Object> completeTaskPayload() {

            Map<String, Object> payload = new HashMap<>();
            payload.put("completed", true);

            return payload;
        }
    }

