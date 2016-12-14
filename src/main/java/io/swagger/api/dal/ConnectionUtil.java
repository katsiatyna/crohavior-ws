package io.swagger.api.dal;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by osboxes on 14/12/16.
 */
public class ConnectionUtil {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    private static JdbcConnectionPool cp;

    static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            if(cp == null){
                cp = JdbcConnectionPool.create(DB_CONNECTION, DB_USER, DB_PASSWORD);
            }
            dbConnection = cp.getConnection();
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
