package com.weatherAPI.apiManager;

import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.weather.apiManager.util.GeoLocationManager;
import java.util.ArrayList;

public class GeoLocationManagerTest {
    public void testGetLocationByIdAddress() {
        WeatherAPIGeoLocation location = GeoLocationManager
                .getLocationByIpAddress("50.189.82.77");
        System.out.println(location.getCity());
        System.out.println(location.getIp());
        System.out.println(location.getLat());
        System.out.println(location.getLongit());
        System.out.println(location.getState());
        System.out.println(location.getZipcode());
    }
    
    public void testGetLocationByCityName() {
        ArrayList<WeatherAPIGeoLocation> locations = 
                new GeoLocationManager().getLocationByCityName("Boston");
        for(WeatherAPIGeoLocation location : locations) {
            System.out.print(location.getCity() + " : ");
            System.out.print(location.getIp() + " : ");
            System.out.print(location.getLat() + " : ");
            System.out.print(location.getLongit() + " : ");
            System.out.print(location.getState() + " : ");
            System.out.print(location.getZipcode() + " : ");
            System.out.println("------------XXXX-----------");
        }
    }
}
