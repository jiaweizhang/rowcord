package rowcord.services;

import org.springframework.transaction.annotation.Transactional;
import rowcord.models.responses.StdResponse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

@Transactional
@org.springframework.stereotype.Service
public class AdminService extends Service {

    public StdResponse upgradeDb() throws IOException {
        String query = readQuery("sql/setup.sql");
        jt.execute(query);
        String procs = readQuery("sql/procs.sql");
        jt.execute(procs);
        return new StdResponse(200, true, "Successfully upgraded database");
    }

    private String readQuery(String file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = in.readLine()) != null) {
            int index;
            // removes single-line comments
            if ((index = str.indexOf("--")) >= 0) {
                str = str.substring(index + 2);
            }
            sb.append(str).append(" ");
        }
        in.close();

        return sb.toString();
    }
}
