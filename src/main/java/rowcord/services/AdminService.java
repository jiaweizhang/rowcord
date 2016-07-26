package rowcord.services;

import rowcord.models.StdResponse;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

@org.springframework.stereotype.Service
public class AdminService extends Service {

    public StdResponse upgradeDb() {
        String query;
        try {
            BufferedReader in = new BufferedReader(new FileReader("sql/setup.sql"));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = in.readLine()) != null) {
                sb.append(str + " ");
            }
            in.close();

            query = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new StdResponse("Bad", "Failed to read SQL file");
        }

        jt.execute(query);

        return new StdResponse("Ok", "Successfully upgraded database");
    }

    public StdResponse tearDown() {
        String query;
        try {
            BufferedReader in = new BufferedReader(new FileReader("sql/teardown.sql"));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = in.readLine()) != null) {
                sb.append(str + " ");
            }
            in.close();

            query = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new StdResponse("Bad", "Failed to read SQL file");
        }

        jt.execute(query);

        return new StdResponse("Ok", "Successfully tore down database");
    }
}
