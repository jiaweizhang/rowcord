package responses;

/**
 * Created by jiawe on 1/16/2016.
 */
public class WeightAdjustedResponse {
    private double lbs = 0;
    private double secs = 0;
    private double meters = 0;

    public WeightAdjustedResponse(double lbs, double secs, double meters) {
        this.lbs = lbs;
        this.secs = secs;
        this.meters = meters;
    }

    public double getLbs() {
        return lbs;
    }

    public double getSecs() {
        return secs;
    }

    public double getMeters() {
        return meters;
    }
}
