package com.weatherAPI.userProfileManager;

import com.weatherAPI.userProfileManager.util.GlobalResources;

import java.sql.*;

public class UserSettings {

    public boolean addKey(String username, String api, String key) {
        boolean success = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("insert into apikeys"
                        + "(username, api, secretkey) "
                        + "VALUES (?,?,?)");
                statement.setString(1, username);
                statement.setString(2, api);
                statement.setString(3, key);
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

        return success;
    }

    public boolean addDefaultLocation(String username, String location) {
        boolean success = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("UPDATE users "
                        + "SET defaultLoc = ? "
                        + "WHERE username = ?");
                statement.setString(1, location);
                statement.setString(2, username);
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

    public boolean addFavoriteLocation(String username, String location) {
        boolean success = false;
            Connection connection = GlobalResources.getConnection();
            PreparedStatement statement = null;

            try {
                if (connection != null) {
                    statement = connection.prepareStatement("insert into favoritelocations"
                            + "(username, location) "
                            + "VALUES (?,?)");
                    statement.setString(1, username);
                    statement.setString(2, location);
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

        return success;
    }

    public boolean updateKey(String username, String api, String key) {
        boolean success = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("UPDATE apikeys "
                        + "SET (secretkey) = ? "
                        + "WHERE username = ? AND api = ? ");
                statement.setString(1, key);
                statement.setString(2, username);
                statement.setString(3, api);
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

        return success;
    }

    public boolean updateDefaultLocation(String username, String location) {
        boolean success = false;

        success = addDefaultLocation(username, location);

        return success;
    }

    public boolean deleteKey(String username, String api, String key) {
        boolean deleted = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("DELETE FROM apikeys "
                        + "WHERE username = ? AND api = ?");
                statement.setString(1, username);
                statement.setString(2, api);
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

    public boolean deleteDefaultLocation(String username, String location) {
        boolean deleted = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("UPDATE users "
                        + "SET defaultLoc = ? "
                        + "WHERE username = ? ");
                statement.setString(1, "NULL");
                statement.setString(2, username);
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

    public boolean deleteFavoriteLocation(String username, String location) {
        boolean deleted = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("DELETE FROM favoritelocations "
                        + "WHERE username = ? AND location = ? ");
                statement.setString(1, username);
                statement.setString(2, location);
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
