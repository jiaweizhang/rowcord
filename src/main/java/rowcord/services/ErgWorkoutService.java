package rowcord.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import requestdata.ergworkout.ErgWorkoutRequest;
import responses.StandardResponse;

import java.util.Date;

/**
 * Created by jiaweizhang on 1/30/16.
 */

@Transactional
@Service
public class ErgWorkoutService extends rowcord.services.Service {

    @Autowired
    private JdbcTemplate jt;

    public StandardResponse addWorkout(ErgWorkoutRequest req, int userId) {
        // check if user exists

        if (!userExists(userId)) {
            return new StandardResponse(true, 1505, "User does not exist");
        }

        // check if timestamp is null and if it is, use current timestamp
        // TODO - currently database is hardcoded to use default timestamp

        // TODO validate all fields

        jt.update(
                "INSERT INTO ergworkouts (user_id, comment, device, heartrate, \"type\", \"time\", distance, rating, split, format, details) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                userId,
                req.getComment(),
                req.getDevice(),
                req.getHeartrate(),
                req.getType(),
                req.getTime(),
                req.getDistance(),
                req.getRating(),
                req.getSplit(),
                req.getFormat(),
                req.getDetails()
        );

        return new StandardResponse(false, 0, "Successfully added workout");
    }

    public StandardResponse editWorkout(@RequestBody final ErgWorkoutRequest req, int userId) {
        // TODO validation
        return null;
    }

    public StandardResponse deleteWorkout(int ergworkoutId, int userId) {
        int workouts = jt.queryForObject(
                "SELECT COUNT(*) FROM ergworkouts WHERE user_id = ? AND ergworkout_id = ?;", Integer.class, userId, ergworkoutId);

        if (workouts != 1) {
            return new StandardResponse(true, 1007, "can't delete workout that's not your own");
        }

        jt.update("DELETE FROM ergworkouts WHERE ergworkout_id = ?", ergworkoutId);
        return new StandardResponse(false, 0, "successfully deleted workout");
    }

    public StandardResponse getById(int ergworkoutId, int userId) {
        // TODO
        return null;
    }

    public StandardResponse complexGet(Date beginDate, Date endDate, int n, String type, String format, int userId) {
        // TODO
        return null;
    }

    public StandardResponse getSummary(int userId) {
        // TODO
        return null;
    }
}