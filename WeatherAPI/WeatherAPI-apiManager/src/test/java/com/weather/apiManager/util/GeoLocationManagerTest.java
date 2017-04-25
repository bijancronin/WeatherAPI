package com.weather.apiManager.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import com.weather.apiManager.command.WeatherAPIGeoLocation;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GeoLocationManagerTest {
    private GeoLocationManager geoLocationManager;
    
    @Before
    public void setUp() {
        geoLocationManager = new GeoLocationManager();
    }
    
    @Test
    public void testGetLocationByCityNameValidCity() {
        ArrayList<WeatherAPIGeoLocation> locations =
                geoLocationManager.getLocationByCityName("Boston");
        int actual = locations.size();
        int expected = 75;
        assertThat(actual, is(expected));
    }
    
    @Test
    public void testGetLocationByCityNameInValidCity() {
        ArrayList<WeatherAPIGeoLocation> locations =
                geoLocationManager.getLocationByCityName("xasxasdasdsaxas");
        int actual = locations.size();
        int expected = 0;
        assertThat(actual, is(expected));
    }
    
    @Test
    public void testGetLocationByIpAddressValid() {
        WeatherAPIGeoLocation actual =
                GeoLocationManager.getLocationByIpAddress("50.189.82.77");
        assertThat(actual, is(notNullValue()));
    }
    
    @Test
    public void testGetLocationByIpAddressInvalid() {
        WeatherAPIGeoLocation actual =
                GeoLocationManager.getLocationByIpAddress("");
        assertThat(actual, is(nullValue()));
    }
}
