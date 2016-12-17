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

public class UserDao {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    private static final String DROP_QUERY = "DROP TABLE IF EXISTS CROHAVIOR_USERS";
    private static final String[] INSERT_QUERY = {"INSERT INTO CROHAVIOR_USERS  VALUES(1, 'AKMA', 'Anas', 'Al Bassit', '623179a7fd3bcdc0428d53f09292641b', 'noway@example.com', '12345678', '360d3ed8b941833a187f6fa6708bbd1c', 'ADMIN')",
                                                    "INSERT INTO CROHAVIOR_USERS  VALUES(2, 'CROHAVIOR', 'Ward', 'Taya', '91a47ceb597e7e6f65335dbb063c26c2', 'ward@example.com', '87654321', '378fd21b4789e9557a39975320b62062', 'USER')"};
    private static final String CREATE_QUERY = "CREATE TABLE CROHAVIOR_USERS (ID  INT PRIMARY KEY, username VARCHAR(255), firstName VARCHAR(255),lastName VARCHAR(255), password VARCHAR(255), email VARCHAR(255), phone VARCHAR(255), api_key VARCHAR(255), user_role VARCHAR(255))";




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
        Integer userId = null;
        String deleteProjectsQuery = "delete from CROHAVIOR_PROJECTS where userId=?";
        String deleteQuery = "delete from CROHAVIOR_USERS where username=?";
        boolean result = false;
        try {
            User user = getUserByName(username);
            if(user != null){
                userId = user.getId();
                deletePreparedStatement = connection.prepareStatement(deleteProjectsQuery);
                deletePreparedStatement.setInt(1, userId);
                int resProjects = deletePreparedStatement.executeUpdate();

            }
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
        boolean updated = false;
        User oldUser = getUserByName(username);
        if(oldUser == null){
            return updated;
        }
        String updateQuery = "update CROHAVIOR_USERS set firstName=?, lastName=?, email=?, password=?, phone=?, user_role=?, api_key=? where username=?";

        try {
            connection.setAutoCommit(false);

            updatePreparedStatement = connection.prepareStatement(updateQuery);
            updatePreparedStatement.setString(1, (user.getFirstName() == null) ? oldUser.getFirstName() : user.getFirstName());

            updatePreparedStatement.setString(2, (user.getLastName() == null) ? oldUser.getLastName() : user.getLastName());
            updatePreparedStatement.setString(3, (user.getEmail() == null) ? oldUser.getEmail() : user.getEmail());
            updatePreparedStatement.setString(4, (user.getPassword() == null) ? oldUser.getPassword() : user.getPassword());
            updatePreparedStatement.setString(5, (user.getPhone() == null) ? oldUser.getPhone() : user.getPhone());
            updatePreparedStatement.setString(6, (user.getUserRole() == null) ? oldUser.getUserRole().toString() : user.getUserRole().toString());
            updatePreparedStatement.setString(7, (user.getApiKey() == null) ? oldUser.getApiKey() : user.getApiKey());
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


    public static User getUserById(Integer userId) throws SQLException {
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement selectPreparedStatement = null;
        Statement stmt = null;
        String SelectQuery = "select * from CROHAVIOR_USERS where id=?";
        User user = null;
        try {
            connection.setAutoCommit(false);

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            selectPreparedStatement.setInt(1, userId);
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

    public static User getUserByApiKey(String apiKey) throws SQLException {
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement selectPreparedStatement = null;
        Statement stmt = null;
        String SelectQuery = "select * from CROHAVIOR_USERS where api_key=?";
        User user = null;
        try {
            connection.setAutoCommit(false);

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            selectPreparedStatement.setString(1, apiKey);
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
}