package responses.subresponses;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jiawe on 1/18/2016.
 */
public class DataGen {
    public static Map createToken(String token) {
        Map<String, String> data = new TreeMap<String, String>();
        data.put("token", token);
        return data;
    }
}
