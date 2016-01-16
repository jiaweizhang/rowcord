package utilities;

/**
 * Created by jiawe on 1/16/2016.
 */
public final class WeightAdjuster {
    private WeightAdjuster() {

    }

    public static double adjust(double lbs, double seconds, double meters) {
        double wf = Math.pow(lbs / 270, 0.222);
        if (meters == 0) {
            return wf * seconds;
        } else if (seconds == 0) {
            return meters / wf;
        } else {
            return 0;
        }
    }
}
