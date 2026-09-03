
package requestBuilder;

import commons.Routes;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class GroupRequestsBuilder {
    public static Response getAllGroups(
            String token,
            int limit,
            int offset,
            boolean activeOnly) {

        return given()
                .header("Authorization", "Bearer " + token)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .queryParam("activeOnly", activeOnly)
                .when()
                .get(Routes.GET_ALL_GROUPS);
    }
    }

