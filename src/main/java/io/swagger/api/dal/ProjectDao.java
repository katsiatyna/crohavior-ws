package io.swagger.api.dal;

/**
 * Created by osboxes on 14/12/16.
 */

import io.swagger.model.Project;
import io.swagger.model.User;
import io.swagger.models.auth.In;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
    private static final String FOREING_KEY = "ALTER TABLE CROHAVIOR_PROJECTS ADD FOREIGN KEY ( userId ) REFERENCES CROHAVIOR_USERS( id ) ;";



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
            stmt.execute(FOREING_KEY);
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

    public static Project getProjectById(Integer projectId) throws SQLException {
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement selectPreparedStatement = null;
        String SelectQuery = "select * from CROHAVIOR_PROJECTS where id=?";
        Project project = null;
        try {
            connection.setAutoCommit(false);

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            selectPreparedStatement.setInt(1, projectId);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 Database select through PreparedStatement");
            if (rs.next()) {
                project = new Project();
                System.out.println("Id "+rs.getInt("id")+" Name "+rs.getString("projectname"));
                project.setId(rs.getInt("id"));
                project.setProjectName(rs.getString("projectName"));
                project.setMinLatitude(rs.getDouble("minLatitude"));
                project.setMinLongitude(rs.getDouble("minLongitude"));
                project.setMaxLatitude(rs.getDouble("maxLatitude"));
                project.setMaxLongitude(rs.getDouble("maxLongitude"));
                project.setUserId(rs.getInt("userId"));
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
        return project;
    }

    public static boolean deleteProjectById(Integer projectId) throws SQLException {
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement deletePreparedStatement = null;
        String deleteQuery = "delete from CROHAVIOR_PROJECTS where Id=?";
        boolean result = false;
        try {
            connection.setAutoCommit(false);

            deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.setInt(1, projectId);
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

    public static boolean updateProjectById(Project project, Integer projectId) throws SQLException {
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement updatePreparedStatement = null;
        String updateQuery = "update CROHAVIOR_PROJECTS set projectName=?, minLatitude=?, minLongitude=?, maxLatitude=?, maxLongitude=? where id=?";
        boolean updated = false;
        Project oldProject = getProjectById(projectId);
        if(oldProject == null){
            return updated;
        }
        try {
            connection.setAutoCommit(false);

            updatePreparedStatement = connection.prepareStatement(updateQuery);

            updatePreparedStatement.setString(1, (project.getProjectName() == null) ? oldProject.getProjectName() : project.getProjectName());
            updatePreparedStatement.setDouble(2, (project.getMinLatitude() == null) ? oldProject.getMinLatitude() : project.getMinLatitude());
            updatePreparedStatement.setDouble(3, (project.getMinLongitude() == null)? oldProject.getMinLongitude() : project.getMinLongitude());
            updatePreparedStatement.setDouble(4, (project.getMaxLatitude() == null) ? oldProject.getMaxLatitude() : project.getMaxLatitude());
            updatePreparedStatement.setDouble(5, (project.getMaxLongitude() == null) ? oldProject.getMaxLongitude() : project.getMaxLongitude());
            updatePreparedStatement.setInt(6, projectId);
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

    public static String createProject(Project project) throws SQLException {
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement updatePreparedStatement = null;
        String updateQuery = "insert into CROHAVIOR_PROJECTS VALUES(?, ?, ?, ?, ?, ?, ?)";
        String message = "ok";
        try {
            connection.setAutoCommit(false);

            updatePreparedStatement = connection.prepareStatement(updateQuery);
            updatePreparedStatement.setInt(1, project.getId());
            updatePreparedStatement.setString(2, project.getProjectName());
            updatePreparedStatement.setDouble(3, project.getMinLatitude());
            updatePreparedStatement.setDouble(4, project.getMinLongitude());
            updatePreparedStatement.setDouble(5, project.getMaxLatitude());
            updatePreparedStatement.setDouble(6, project.getMaxLongitude());
            updatePreparedStatement.setInt(7, project.getUserId());
            int res = updatePreparedStatement.executeUpdate();
            System.out.println("H2 Database UPDATE through PreparedStatement");
            if (res > 0) {
                System.out.println(res);
            } else {
                message = "Unknown error";
            }
            updatePreparedStatement.close();

            connection.commit();
            return message;
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            return e.getLocalizedMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        } finally {
            connection.close();
        }
    }


    public static List<Project> getProjectsByUserId(Integer userId) throws SQLException {
        List<Project> projects = new ArrayList<>();
        Connection connection = ConnectionUtil.getDBConnection();
        PreparedStatement selectPreparedStatement = null;
        String SelectQuery = "select * from CROHAVIOR_PROJECTS where userId=?";
        try {
            connection.setAutoCommit(false);

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            selectPreparedStatement.setInt(1, userId);
            ResultSet rs = selectPreparedStatement.executeQuery();
            System.out.println("H2 Database select through PreparedStatement");
            while (rs.next()) {
                Project project = new Project();
                System.out.println("Id "+rs.getInt("id")+" Name "+rs.getString("projectname"));
                project.setId(rs.getInt("id"));
                project.setProjectName(rs.getString("projectName"));
                project.setMinLatitude(rs.getDouble("minLatitude"));
                project.setMinLongitude(rs.getDouble("minLongitude"));
                project.setMaxLatitude(rs.getDouble("maxLatitude"));
                project.setMaxLongitude(rs.getDouble("maxLongitude"));
                project.setUserId(rs.getInt("userId"));
                projects.add(project);
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
        return projects;
    }
}