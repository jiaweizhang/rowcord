package responses.data.ergworkout;

/**
 * Created by jiaweizhang on 1/30/16.
 */
public class SummaryData {
    private int time;
    private int distance;
    private int count;

    public SummaryData(int time, int distance, int count) {
        this.time = time;
        this.distance = distance;
        this.count = count;
    }

    public int getTime() {
        return time;
    }

    public int getDistance() {
        return distance;
    }

    public int getCount() {
        return count;
    }
}
