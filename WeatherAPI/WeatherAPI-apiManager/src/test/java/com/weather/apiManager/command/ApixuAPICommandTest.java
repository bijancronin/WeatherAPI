package com.weather.apiManager.command;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ApixuAPICommandTest {
    
    private WeatherAPIKey key1, key2;
    private WeatherAPIGeoLocation location1, location2;
    private WeatherAPICommand apixu1, apixu2;
    
    @Before
    public void setUp() {
        key1 = new WeatherAPIKey("d882133b26e248fe91f192249171503");
        location1 = new WeatherAPIGeoLocation();
        location1.setLat(37.8267);
        location1.setLongit(-122.4233);
        location1.setCity("");
        location1.setState("");
        location1.setCountry("");
        apixu1 = new ApixuAPICommand(key1, location1);
        key2 = new WeatherAPIKey("invalid");
        location2 = new WeatherAPIGeoLocation();
        location2.setLat(42.3601);
        location2.setLongit(71.0589);
        location2.setCity("");
        location2.setState("");
        location2.setCountry("");
        apixu2 = new ApixuAPICommand(key2, location2);
    }
    
    @Test
    public void testExecuteSuccess() {
        String response = apixu1.execute();
        assertThat(response, is(notNullValue()));
        
        // Need to check if response is coming from DB.
        // Since in previous request the response must have
        // been saved to DB.
        String responseFromDB = apixu1.execute();
        assertThat(response, is(responseFromDB));
    }
    
    @Test
    public void testExecuteFailure() {
        String response = apixu2.execute();
        assertThat(response, is(nullValue()));
    }
}
