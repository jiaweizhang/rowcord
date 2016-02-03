package requestdata.ergworkout;

import java.sql.Timestamp;

/**
 * Created by jiaweizhang on 1/30/16.
 */

public class ErgWorkoutRequest {
    private Long workoutdate; // change to timestamp
    private String comment;
    private String device;
    private Integer heartrate;
    private String type;
    private Integer time;
    private Integer distance;
    private Integer rating;
    private Integer split;
    private String format;
    private String details;

    public boolean isValid() {
        return device != null && heartrate != null && type != null && time != null && distance != null && rating != null && split != null && format != null && details != null;
    }

    public Long getWorkoutdate() {
        return workoutdate;
    }

    public String getComment() {
        return comment;
    }

    public String getDevice() {
        return device;
    }

    public Integer getHeartrate() {
        return heartrate;
    }

    public String getType() {
        return type;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getSplit() {
        return split;
    }

    public String getFormat() {
        return format;
    }

    public String getDetails() {
        return details;
    }
}
