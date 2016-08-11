package rowcord.models.responses;

import java.util.Map;

/**
 * Created by Jiawei on 7/26/2016.
 */
public class GroupSearchResponse extends StdResponse {
    public Map<Long, String> searchResults;

    public GroupSearchResponse(int status, boolean success, String message, Map<Long, String> searchResults) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.searchResults = searchResults;
    }
}
