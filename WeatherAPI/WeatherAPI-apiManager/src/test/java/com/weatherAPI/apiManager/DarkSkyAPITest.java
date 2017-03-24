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
        String darkSkyKeyString = "e80440fb1812b94394324d93d488f300";
		
		WeatherAPIKey dSK = new WeatherAPIKey(darkSkyKeyString);
		DarkSkyAPICommand darkSky = new DarkSkyAPICommand();
		
		darkSky.setKey(dSK);
		
		WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
		
		location.setLat(37.8267);
		location.setLongit(-122.4233);
		
		String json = darkSky.execute(darkSky.getKey(), location);
        System.out.println(json);
    }
}
