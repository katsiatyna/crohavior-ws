package io.swagger.api.dal;

/**
 * Created by osboxes on 14/12/16.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.swagger.model.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.DeleteDbFiles;


// H2 Database Example

public class UserDal {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    private static final String DROP_QUERY = "DROP TABLE IF EXISTS CROHAVIOR_USERS";
    private static final String[] INSERT_QUERY = {"INSERT INTO CROHAVIOR_USERS  VALUES(1, 'AKMA', 'Anas', 'Al Bassit', '623179a7fd3bcdc0428d53f09292641b', 'noway@example.com', '12345678', 'cbb11ed87dc8a95d81400c7f33c7c171', 'ADMIN')",
                                                    "INSERT INTO CROHAVIOR_USERS  VALUES(2, 'CROHAVIOR', 'Ward', 'Taya', '91a47ceb597e7e6f65335dbb063c26c2', 'ward@example.com', '87654321', '91a47ceb597e7e6f65335dbb063c26c2', 'USER')"};
    private static final String CREATE_QUERY = "CREATE TABLE CROHAVIOR_USERS (ID  INT PRIMARY KEY, username VARCHAR(255), firstName VARCHAR(255),lastName VARCHAR(255), password VARCHAR(255), email VARCHAR(255), phone VARCHAR(255), api_key VARCHAR(255), user_role VARCHAR(255))";

    private static JdbcConnectionPool cp;


    public static void main(String[] args) throws Exception {
        try {
           /* // delete the H2 database named 'test' in the user home directory
            DeleteDbFiles.execute("~", "test", true);
            insertWithStatement();
            DeleteDbFiles.execute("~", "test", true);
            insertWithPreparedStatement();*/
           initTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void initTable() throws SQLException {
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.execute(DROP_QUERY);
            stmt.execute(CREATE_QUERY);
            for(String query: INSERT_QUERY){
                stmt.execute(query);
            }
            connection.commit();
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

    }

    public static User getUserByName(String username) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement selectPreparedStatement = null;
        Statement stmt = null;
        String SelectQuery = "select * from CROHAVIOR_USERS where username=?";
        User user = null;
        try {
            connection.setAutoCommit(false);

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            selectPreparedStatement.setString(1, username);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 Database select through PreparedStatement");
            if (rs.next()) {
                user = new User();
                System.out.println("Id "+rs.getInt("id")+" Name "+rs.getString("username"));
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setPassword("******");
                user.setApiKey("******");
                user.setUserRole(User.UserRoleEnum.valueOf(rs.getString("user_role")));
            }
            System.out.println(user.toString());
            selectPreparedStatement.close();

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return user;
    }

    public static boolean deleteUserByName(String username) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement deletePreparedStatement = null;
        String SelectQuery = "delete from CROHAVIOR_USERS where username=?";
        try {
            connection.setAutoCommit(false);

            deletePreparedStatement = connection.prepareStatement(SelectQuery);
            deletePreparedStatement.setString(1, username);
            int res = deletePreparedStatement.executeUpdate();
            System.out.println("H2 Database DELETE through PreparedStatement");
            if (res > 0) {
                System.out.println(res);
            }
            deletePreparedStatement.close();

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return true;
    }



    // H2 SQL Prepared Statement Example
    private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;
        PreparedStatement insertPreparedStatement = null;
        PreparedStatement selectPreparedStatement = null;

        String CreateQuery = "CREATE TABLE PERSON(id int primary key, name varchar(255))";
        String InsertQuery = "INSERT INTO PERSON" + "(id, name) values" + "(?,?)";
        String SelectQuery = "select * from PERSON";
        try {
            connection.setAutoCommit(false);

            createPreparedStatement = connection.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();

            insertPreparedStatement = connection.prepareStatement(InsertQuery);
            insertPreparedStatement.setInt(1, 1);
            insertPreparedStatement.setString(2, "Jose");
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 Database inserted through PreparedStatement");
            while (rs.next()) {
                System.out.println("Id "+rs.getInt("id")+" Name "+rs.getString("name"));
            }
            selectPreparedStatement.close();

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    // H2 SQL Statement Example
    private static void insertWithStatement() throws SQLException {
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE PERSON(id int primary key, name varchar(255))");
            stmt.execute("INSERT INTO PERSON(id, name) VALUES(1, 'Anju')");
            stmt.execute("INSERT INTO PERSON(id, name) VALUES(2, 'Sonia')");
            stmt.execute("INSERT INTO PERSON(id, name) VALUES(3, 'Asha')");

            ResultSet rs = stmt.executeQuery("select * from PERSON");
            System.out.println("H2 Database inserted through Statement");
            while (rs.next()) {
                System.out.println("Id "+rs.getInt("id")+" Name "+rs.getString("name"));
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    private static Connection getDBConnection() {
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