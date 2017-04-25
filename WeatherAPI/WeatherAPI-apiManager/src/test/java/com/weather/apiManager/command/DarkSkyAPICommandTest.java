package com.weather.apiManager.command;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;

import org.junit.Test;

public class DarkSkyAPICommandTest {
    
    private WeatherAPIKey key1, key2;
    private WeatherAPIGeoLocation location1, location2;
    private WeatherAPICommand darkSky1, darkSky2;
    
    @Before
    public void setUp() {
        key1 = new WeatherAPIKey("e80440fb1812b94394324d93d488f300");
        location1 = new WeatherAPIGeoLocation();
        location1.setLat(37.8267);
        location1.setLongit(-122.4233);
        location1.setCity("");
        location1.setState("");
        location1.setCountry("");
        darkSky1 = new DarkSkyAPICommand(key1, location1);
        key2 = new WeatherAPIKey("invalid");
        location2 = new WeatherAPIGeoLocation();
        location2.setLat(42.3601);
        location2.setLongit(71.0589);
        location2.setCity("");
        location2.setState("");
        location2.setCountry("");
        darkSky2 = new DarkSkyAPICommand(key2, location2);
    }
    
    @Test
    public void testExecuteSuccess() {
        String response = darkSky1.execute();
        assertThat(response, is(notNullValue()));
        
        // Need to check if response is coming from DB.
        // Since in previous request the response must have
        // been saved to DB.
        String responseFromDB = darkSky1.execute();
        assertThat(response, is(responseFromDB));
    }
    
    @Test
    public void testExecuteFailure() {
        String response = darkSky2.execute();
        assertThat(response, is(nullValue()));
    }
}
