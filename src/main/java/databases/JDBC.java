package databases;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by jiawe on 1/16/2016.
 */
public class JDBC {

    public String connect() {

        // MySQL
        /*String url = "jdbc:mysql://localhost:3306/";
        String dbName = "testdb";
        String driver = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "password";*/

        // PostgreSQL
        String url = "jdbc:postgresql://localhost:5432/";
        String dbName = "testdb";
        String driver = "org.postgresql.Driver";
        String username = "postgres";
        String password = "password";

        // MySQL
        /*System.out.println("Started connection attempt");
        Connection c = null;
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed on Class.forName()";
        }

        try {
            Connection conn = DriverManager.getConnection(url + dbName, username, password);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed on getConnection()";
        }
        System.out.println("Opened database successfully");
        return "Success";*/

        // PostgreSQL
        System.out.println("Started connection attempt");
        Connection c = null;
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed on Class.forName()";
        }

        try {
            c = DriverManager.getConnection(url + dbName, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed on getConnection()";
        }
        System.out.println("Opened database successfully");
        return "Success";
    }
}