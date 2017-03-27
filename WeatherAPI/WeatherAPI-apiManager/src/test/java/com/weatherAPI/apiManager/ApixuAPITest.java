package com.weatherAPI.apiManager;

import com.weather.apiManager.command.ApixuAPICommand;
import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.weather.apiManager.command.WeatherAPIKey;

/**
 * Need to convert these test cases to JUnit at some point.
 * Leaving it as is for now. Did this for testing whether response is in
 * correct format or not.
 */
public class ApixuAPITest {
    public void testExecute() {
        WeatherAPIKey key = new WeatherAPIKey("d882133b26e248fe91f192249171503");
        WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
        location.setLat(37.8267);
		location.setLongit(-122.4233);
        ApixuAPICommand command = new ApixuAPICommand(key, location);
        String response = command.execute();
        System.out.println(response);
    }
}
