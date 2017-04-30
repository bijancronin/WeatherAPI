package com.weatherAPI.userProfileManager;

import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.weatherAPI.userProfileManager.util.GlobalResources;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class UserSettings {

    public boolean addSubscription(String username, String api) {
        boolean success = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("insert into api_subscription"
                        + "(subscription_id, username, api_name) "
                        + "VALUES (?,?,?)");
                statement.setString(1, UUID.randomUUID().toString());
                statement.setString(2, username);
                statement.setString(3, api);
                statement.execute();

                success = true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding API Subscription");
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
    
    public boolean addSubscription(String username, String api, String apiKey) {
        boolean success = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("insert into api_subscription"
                        + "(subscription_id, username, api_name, api_key) "
                        + "VALUES (?,?,?,?)");
                statement.setString(1, UUID.randomUUID().toString());
                statement.setString(2, username);
                statement.setString(3, api);
                statement.setString(4, apiKey);
                statement.execute();

                success = true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding API Subscription");
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

    public boolean addDefaultLocation(String username, WeatherAPIGeoLocation location) {
        boolean success = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("UPDATE users "
                        + "SET default_latitude = ?, default_longitude = ?, default_city = ? "
                        + ", default_state=?, default_country=?, default_zipcode=? "
                        + "WHERE username = ?");
                statement.setDouble(1, location.getLat());
                statement.setDouble(2, location.getLongit());
                statement.setString(3, location.getCity());
                statement.setString(4, location.getState());
                statement.setString(5, location.getCountry());
                statement.setString(6, location.getZipcode());
                statement.setString(7, username);
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

    public boolean addFavoriteLocation(String username, WeatherAPIGeoLocation location) {
        boolean success = false;
            Connection connection = GlobalResources.getConnection();
            PreparedStatement statement = null;

            try {
                if (connection != null) {
                    statement = connection.prepareStatement("insert into favorite_locations "
                            + "(location_id, username, latitude, longitude, city, state, country, zipcode) "
                            + "VALUES (?,?,?,?,?,?,?,?)");
                    statement.setString(1, UUID.randomUUID().toString());
                    statement.setString(2, username);
                    statement.setDouble(3, location.getLat());
                    statement.setDouble(4, location.getLongit());
                    statement.setString(5, location.getCity());
                    statement.setString(6, location.getState());
                    statement.setString(7, location.getCountry());
                    statement.setString(8, location.getZipcode());
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

    public boolean deleteSubscriptions(String username) {
        boolean deleted = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("DELETE FROM api_subscription "
                        + "WHERE username = ?");
                statement.setString(1, username);
                statement.execute();

                deleted = true;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting subscription");
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

    public boolean deleteDefaultLocation(String username) {
        boolean deleted = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("UPDATE users "
                        + "SET default_latitude = ?, default_longitude=?, default_city=? "
                        + ", default_state=?, default_country=?, default_zipcode=? "
                        + "WHERE username = ? ");
                statement.setNull(1, java.sql.Types.DOUBLE);
                statement.setNull(2, java.sql.Types.DOUBLE);
                statement.setNull(3, java.sql.Types.VARCHAR);
                statement.setNull(4, java.sql.Types.VARCHAR);
                statement.setNull(5, java.sql.Types.VARCHAR);
                statement.setNull(6, java.sql.Types.VARCHAR);
                statement.setString(7, username);
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
    
    public boolean deleteFavoriteLocation(String locationId) {
        boolean deleted = false;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("DELETE FROM favorite_locations "
                        + "WHERE location_id = ?");
                statement.setString(1, locationId);
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
    
    public ArrayList<String> getUserAPISubscriptions(String username) {
        ArrayList<String> subscriptions = new ArrayList<>(0);
        ResultSet result;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("select * from api_subscription "
                        + "where username = ?");
                statement.setString(1, username);
                result = statement.executeQuery();

                while (result.next()) {
                    subscriptions.add(result.getString("api_name"));
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

        return subscriptions;
    }
    
    public ArrayList<WeatherAPIGeoLocation> getUserFavoriteLocation(String username) {
        ArrayList<WeatherAPIGeoLocation> locations = new ArrayList<>(0);
        ResultSet result;

        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("select * from favorite_locations "
                        + "where username = ?");
                statement.setString(1, username);
                result = statement.executeQuery();

                while (result.next()) {
                    WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
                    location.setLocationId(result.getString("location_id"));
                    location.setLat(result.getDouble("latitude"));
                    location.setLongit(result.getDouble("longitude"));
                    location.setCity(result.getString("city"));
                    location.setState(result.getString("state"));
                    location.setCountry(result.getString("country"));
                    location.setZipcode(result.getString("zipcode"));
                    locations.add(location);
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

        return locations;
    }
    
    public WeatherAPIGeoLocation getUserDefaultLocation(String username) {
        WeatherAPIGeoLocation location = null;
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
                    Double latitude = result.getDouble("default_latitude");
                    Double longitude = result.getDouble("default_longitude");
                    String city = result.getString("default_city");
                    String state = result.getString("default_state");
                    String country = result.getString("default_country");
                    String zipcode = result.getString("default_zipcode");
                    if(latitude != 0 && longitude != 0 && city != null
                            && state != null && country != null && zipcode != null) {
                        location = new WeatherAPIGeoLocation();
                        location.setLat(latitude);
                        location.setLongit(longitude);
                        location.setCity(city);
                        location.setState(state);
                        location.setCountry(country);
                        location.setZipcode(zipcode);
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

        return location;
    }
}