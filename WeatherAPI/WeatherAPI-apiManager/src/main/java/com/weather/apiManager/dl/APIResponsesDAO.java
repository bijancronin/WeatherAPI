package com.weather.apiManager.dl;

import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.weather.apiManager.util.GlobalResources;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.UUID;

/**
 * A class for accessing database(api_responses table).
 */
public class APIResponsesDAO {
    
    private static final long MS_IN_ONE_HOUR = 3600000;
    
    public static final String DARK_SKY = "darksky";
    public static final String APIXU = "apixu";
    public static final String WUNDER = "wunder";
    
    /**
     * A method for adding API response.
     * @param bean
     * @return true if record is added else false.
     */
    public boolean addApiResponse(APIResponseBean bean) {
        boolean isAdded = false;
        
        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;
        
        try {
            if(connection != null) {
                statement = connection.prepareStatement("insert into api_responses"
                        + "(request_id, latitude, longitude, city, state, api, json, request_time)"
                        + " values(?,?,?,?,?,?)");
                statement.setString(1, UUID.randomUUID().toString());
                statement.setDouble(2, bean.getLatitude());
                statement.setDouble(3, bean.getLongitude());
                statement.setString(4, bean.getApi());
                statement.setString(5, bean.getJson());
                tatement.setString(6, bean.getApi());
                statement.setString(7, bean.getJson());
                statement.setTimestamp(8, bean.getRequestTime());
                statement.executeUpdate();
                isAdded = true;
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
        
        return isAdded;
    }
        
    /**
     * A method for fetching JSON response from database for darksky api.
     * It deletes the matching record if it is older than 1 hour.
     * Returns null of no matching record is found in the database.
     * @param api
     * @param location
     * @return 
     */
    public String getAPIResponse(String api, WeatherAPIGeoLocation location) {
        String json = null;
        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            if(connection != null) {
                statement = connection.prepareStatement(
                        "select request_id, json, request_time from api_responses "
                                + "where api=? and latitude=? and longitude=?");
                statement.setString(1, api);
                statement.setDouble(2, location.getLat());
                statement.setDouble(3, location.getLongit());
                resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    long requestTimeinMS = resultSet.getTimestamp("request_time")
                            .getTime();
                    long currentTimeInMS = Calendar.getInstance().getTimeInMillis();
                    if((currentTimeInMS - requestTimeinMS) > MS_IN_ONE_HOUR) {
                        deleteAPIResponseByRequestId(resultSet.getString("request_id"));
                    } else {
                        json = resultSet.getString("json");
                    }
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(resultSet != null) resultSet.close();
                if(statement != null) statement.close();
                if(connection != null) connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        
        return json;
    }
    
    private void deleteAPIResponseByRequestId(String requestId) {
        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            if(connection != null) {
                statement = connection.prepareStatement(
                        "delete from api_responses where request_id=?");
                statement.setString(1, requestId);
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(resultSet != null) resultSet.close();
                if(statement != null) statement.close();
                if(connection != null) connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
