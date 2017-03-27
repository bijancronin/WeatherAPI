package com.weatherAPI.apiManager;

import com.weather.apiManager.command.DarkSkyAPICommand;
import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.weather.apiManager.command.WeatherAPIKey;

/**
 * Need to convert these test cases to JUnit at some point.
 * Leaving it as is for now. Did this for testing whether response is in
 * correct format or not.
 */
public class DarkSkyAPITest {
    public void testExecute() {
        WeatherAPIKey key = new WeatherAPIKey("e80440fb1812b94394324d93d488f300");
        WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
        location.setLat(37.8267);
		location.setLongit(-122.4233);
        DarkSkyAPICommand darkSky = new DarkSkyAPICommand(key, location);
	String json = darkSky.execute();
        System.out.println(json);
    }
}
