package com.weather.apiManager.util;

import com.maxmind.geoip.Location;
import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.maxmind.geoip.LookupService;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

/**
 * This class is reponsible for converting IP/City to latitude
 * and longitude.
 * @author Aditya Solge
 */
public class GeoLocationManager {
    
    private static LookupService lookup;
    
    static {
        try {
            File geoLiteCityFile = File.createTempFile("GeoLiteCity", "dat");
            geoLiteCityFile.deleteOnExit();
            FileUtils.copyInputStreamToFile(GeoLocationManager.class
                    .getResourceAsStream("/GeoLiteCity.dat"), geoLiteCityFile);
            lookup = new LookupService(geoLiteCityFile
                    ,LookupService.GEOIP_MEMORY_CACHE);
            System.out.println("[GeoLiteDB Info] " + lookup.getDatabaseInfo());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<WeatherAPIGeoLocation> getLocationByCityName(String cityName) {
        ArrayList<WeatherAPIGeoLocation> locations = new ArrayList<>(0);
        
        ResultSet result;
        Connection connection = GlobalResources.getConnection();
        PreparedStatement statement = null;

        try {
            if (connection != null) {
                statement = connection.prepareStatement("select * from geo_lite "
                        + "where city = ?");
                statement.setString(1, cityName);
                result = statement.executeQuery();

                while (result.next()) {
                    WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
                    location.setCity(result.getString("city"));
                    location.setLat(result.getDouble("latitude"));
                    location.setLongit(result.getDouble("longitude"));
                    location.setState(result.getString("region"));
                    location.setZipcode(result.getString("postal_code"));
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
    
    public static WeatherAPIGeoLocation getLocationByIpAddress(String ipAddress) {
        WeatherAPIGeoLocation geoLocation = null;
        
        Location location = lookup.getLocation(ipAddress);
        if(location != null) {
            geoLocation = new WeatherAPIGeoLocation();
            geoLocation.setCity(location.city);
            geoLocation.setIp(ipAddress);
            geoLocation.setLat(location.latitude);
            geoLocation.setLongit(location.longitude);
            geoLocation.setState(location.region);
            geoLocation.setZipcode(location.postalCode);
        }
        
        return geoLocation;
    }
}
