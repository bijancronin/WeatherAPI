package com.weatherAPI.userProfileManager;

import com.weatherAPI.userProfileManager.util.GlobalResources;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.sql.*;

public class UserProfile {

    public boolean createUser(String username, String name, String password) {
        boolean success = false;
        if (!checkUsernameExists(username)) {
            Connection connection = GlobalResources.getConnection();
            PreparedStatement statement = null;

            try {
                if (connection != null) {
                    statement = connection.prepareStatement("insert into users "
                            + "(username, password, name) "
                            + "VALUES (?,?,?)");
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.setString(3, name);
                    statement.execute();

                    success = true;
                }
            } catch (SQLException e) {
                System.out.println("Error Creating User");
            } finally {
                try {
                    if (statement != null) statement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }
    
    public UserProfileBean getUserDetailsByUsername(String username) {
        UserProfileBean userProfileBean = null;
        ResultSet result;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("select * from users "
                        + "where username = ?");
                statement.setString(1, username);
                result = statement.executeQuery();

                if (result.next()) {
                    userProfileBean = new UserProfileBean();
                    userProfileBean.setName(result.getString("name"));
                    userProfileBean.setUsername(result.getString("username"));
                    userProfileBean.setPassword(result.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userProfileBean;
    }

    public boolean updateUser(String name, String username, String password) {
        boolean success = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("UPDATE users "
                        + "SET password = ?, name = ? "
                        + "WHERE username = ?");
                statement.setString(1, password);
                statement.setString(2, name);
                statement.setString(3, username);
                statement.execute();

                success = true;
            }
        } catch (SQLException e) {
            System.out.println("Error Updating user");
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    public boolean checkUsernameExists(String username) {
        boolean exists = false;
        ResultSet result;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if(connection != null) {
                statement = connection.prepareStatement("select username from users "
                        + "where username=?");
                statement.setString(1, username);
                result = statement.executeQuery();

                if (result.next()) {
                    exists = true;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) statement.close();
                if(connection != null) connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return exists;
    }

    public UserProfileBean authenticateUser(String username, String password) {
        UserProfileBean userProfileBean = null;
        ResultSet result;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("select * from users "
                        + "where username = ?");
                statement.setString(1, username);
                result = statement.executeQuery();

                if (result.next()) {
                    String pw = result.getString("password");
                    if (pw.equals(password)) {
                        userProfileBean = new UserProfileBean();
                        userProfileBean.setName(result.getString("name"));
                        userProfileBean.setUsername(result.getString("username"));
                        userProfileBean.setPassword(result.getString("password"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userProfileBean;
    }

    public boolean deleteUser(String username) {
        boolean deleted = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("DELETE FROM users "
                        + "WHERE username = ?");
                statement.setString(1, username);
                statement.execute();

                deleted = true;
            }
        } catch (SQLException e) {
            System.out.println("Error Updating user");
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return deleted;
    }

}
