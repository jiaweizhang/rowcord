package responses;

/**
 * Created by jiawe on 1/16/2016.
 */
public class JsonResponse {

    private String status = "";
    private String message = "";

    public JsonResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}