package databases;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by jiawe on 1/16/2016.
 */
public class PostgreSQLJDBC {

    public static void connect() {
        System.out.println("Started connection attempt");
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/testdb",
                            "root", "password");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}