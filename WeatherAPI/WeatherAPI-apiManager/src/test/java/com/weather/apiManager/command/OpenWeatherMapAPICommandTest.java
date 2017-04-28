package com.weather.apiManager.command;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ForecaAPICommandTest {
	
    private WeatherAPIKey key1, key2;
    private WeatherAPIGeoLocation location1, location2;
    private WeatherAPICommand api1, api2;
    
    @Before
    public void setUp() {
        key1 = new WeatherAPIKey("995ae1c7e4573cf3578dd394ac69c532");
        location1 = new WeatherAPIGeoLocation();
        location1.setLat(37.8267);
        location1.setLongit(-122.4233);
        location1.setCity("");
        location1.setState("");
        location1.setCountry("");
        api1 = new OpenWeatherMapAPICommand(key1, location1);
        key2 = new WeatherAPIKey("invalid");
        location2 = new WeatherAPIGeoLocation();
        location2.setLat(42.3601);
        location2.setLongit(71.0589);
        location2.setCity("");
        location2.setState("");
        location2.setCountry("");
        api2 = new OpenWeatherMapAPICommand(key2, location2);
    }
    
    @Test
    public void testExecuteSuccess() {
        String response = api1.execute();
        assertThat(response, is(notNullValue()));
        
        // Need to check if response is coming from DB.
        // Since in previous request the response must have
        // been saved to DB.
        String responseFromDB = api1.execute();
        assertThat(response, is(responseFromDB));
    }
    
    @Test
    public void testExecuteFailure() {
        String response = api2.execute();
        assertThat(response, is(nullValue()));
    }
    
}
