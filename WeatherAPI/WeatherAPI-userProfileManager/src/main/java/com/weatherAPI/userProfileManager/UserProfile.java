package com.weatherAPI.userProfileManager;

import com.weatherAPI.userProfileManager.util.GlobalResources;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class UserProfile {

    public boolean createUser(String username, String name, String password) {
        boolean success = false;
        if (!checkUsernameExists(username)) {
            Connection connection = GlobalResources.getConnection();
            PreparedStatement statement = null;

            byte[] salt = new byte[16];
            Random random = new Random();
            random.nextBytes(salt);

            byte[] hashedPassword = hashPassword(password.toCharArray(), salt, 1024, 32);

            try {
                if (connection != null) {
                    statement = connection.prepareStatement("insert into users "
                            + "(username, name, password, salt) "
                            + "VALUES (?,?,?,?)");
                    statement.setString(1, username);
                    statement.setString(2, name);
                    statement.setBytes(3, hashedPassword);
                    statement.setBytes(4, salt);
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
                    userProfileBean.setPassword(result.getBytes("password"));
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

        byte[] salt = new byte[16];
        Random random = new Random();
        random.nextBytes(salt);

        byte[] hashedPassword = hashPassword(password.toCharArray(), salt, 1024, 32);

        try {
            if (connection != null) {
                statement = connection.prepareStatement("UPDATE users "
                        + "SET password = ?, salt = ?,name = ? "
                        + "WHERE username = ?");
                statement.setBytes(1, hashedPassword);
                statement.setBytes(2, salt);
                statement.setString(3, name);
                statement.setString(4, username);
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
                    byte[] pw = result.getBytes("password");
                    byte[] salt = result.getBytes("salt");

                    byte[] hashedPassword = hashPassword(password.toCharArray(), salt, 1024, 32);

                    if (Arrays.equals(pw, hashedPassword)) {
                        userProfileBean = new UserProfileBean();
                        userProfileBean.setName(result.getString("name"));
                        userProfileBean.setUsername(result.getString("username"));
                        userProfileBean.setPassword(result.getBytes("password"));
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

    private static byte[] hashPassword(char[] password, byte[] salt, int iterations, int keyLength) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return res;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

}
