package utilities;

import responses.subresponses.WeightAdjustedResponse;

/**
 * Created by jiawe on 1/16/2016.
 */
public final class WeightAdjuster {
    private WeightAdjuster() {

    }

    public static WeightAdjustedResponse adjust(double lbs, double seconds, double meters) {
        double wf = Math.pow(lbs / 270, 0.222);
        double outputSecs = 0;
        double outputMeters = 0;
        if (meters == 0) {
            outputSecs = wf * seconds;
        } else if (seconds == 0) {
            outputMeters = meters / wf;
        }
        return new WeightAdjustedResponse(lbs, outputSecs, outputMeters);
    }
}
