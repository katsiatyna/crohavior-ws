package io.swagger.api.dal;

/**
 * Created by osboxes on 14/12/16.
 */

import io.swagger.model.User;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;


// H2 Database Example

public class ProjectDao {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    private static final String DROP_QUERY = "DROP TABLE IF EXISTS CROHAVIOR_PROJECTS";
    private static final String[] INSERT_QUERY = {"INSERT INTO CROHAVIOR_PROJECTS  VALUES(1, 'AKMA_HOUSING', 39.9, 116.2, 39.99, 116.5, 1)",
                                                    "INSERT INTO CROHAVIOR_PROJECTS  VALUES(2, 'CROHAVIOR', 39.125, 116.675, 39.375, 116.995, 2)"};
    private static final String CREATE_QUERY = "CREATE TABLE CROHAVIOR_PROJECTS (ID  INT PRIMARY KEY, projectName VARCHAR(255), minLatitude DOUBLE,minLongitude DOUBLE, maxLatitude DOUBLE, maxLongitude DOUBLE, userId INT NOT NULL)";



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
        Connection connection = ConnectionUtil.getDBConnection();
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
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement selectPreparedStatement = null;
        Statement stmt = null;
        String SelectQuery = "select * from CROHAVIOR_PROJECTS where username=?";
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
            //System.out.println(user.toString());
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
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement deletePreparedStatement = null;
        String deleteQuery = "delete from CROHAVIOR_PROJECTS where username=?";
        boolean result = false;
        try {
            connection.setAutoCommit(false);

            deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.setString(1, username);
            int res = deletePreparedStatement.executeUpdate();
            System.out.println("H2 Database DELETE through PreparedStatement");
            if (res > 0) {
                result = true;
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
        return result;
    }

    public static boolean updateUserByName(User user, String username) throws SQLException {
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement updatePreparedStatement = null;
        String updateQuery = "update CROHAVIOR_PROJECTS set firstName=?, lastName=?, email=?, password=?, phone=?, user_role=?, api_key=? where username=?";
        boolean updated = false;
        try {
            connection.setAutoCommit(false);

            updatePreparedStatement = connection.prepareStatement(updateQuery);
            updatePreparedStatement.setString(1, user.getFirstName());
            updatePreparedStatement.setString(2, user.getLastName());
            updatePreparedStatement.setString(3, user.getEmail());
            updatePreparedStatement.setString(4, user.getPassword());
            updatePreparedStatement.setString(5, user.getPhone());
            updatePreparedStatement.setString(6, user.getUserRole().toString());
            updatePreparedStatement.setString(7, user.getApiKey());
            updatePreparedStatement.setString(8, username);
            int res = updatePreparedStatement.executeUpdate();
            System.out.println("H2 Database UPDATE through PreparedStatement");
            if (res > 0) {
                updated = true;
                System.out.println(res);
            }
            updatePreparedStatement.close();

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return updated;
    }


}