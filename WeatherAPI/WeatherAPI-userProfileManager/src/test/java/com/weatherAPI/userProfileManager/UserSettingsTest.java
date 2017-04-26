package com.weatherAPI.userProfileManager;

import com.weather.apiManager.command.WeatherAPIGeoLocation;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Test;

/**
 * Created by HaeYoung on 4/2/2017.
 */
public class UserSettingsTest {
    private UserSettings settings;
    private UserProfile profile;
    private String username;
    private WeatherAPIGeoLocation location;

    @Before
    public void setup() {
        settings = new UserSettings();
        assertNotNull(settings);
        
        location = new WeatherAPIGeoLocation();
        location.setLat(37.8267);
        location.setLongit(-122.4233);
        location.setCity("Boston");
        location.setState("MA");
        location.setCountry("USA");
        location.setZipcode("02125");

        username = "TestUser@testerz.com";
        String name = "Tester McTestTest";
        String password = "ITestStuff";
        profile = new UserProfile();
        profile.createUser(username, name, password);
    }
    
    @Test
    public void testAddSubscription() {
        boolean actual = settings.addSubscription(username, "darksky");
        assertTrue(actual);
        
        settings.deleteSubscriptions(username);
    }
    
    @Test
    public void testAddSubscriptionWithPrivateKey() {
        boolean actual = settings.addSubscription(username
                , "darksky", "private_key");
        assertTrue(actual);
    }
    
    @Test
    public void testAddDefaultLocationAndDeleteDefaultLocation() {
        boolean actual1 = settings.addDefaultLocation(username, location);
        assertTrue(actual1);
        
        boolean actual2 = settings.deleteDefaultLocation(username);
        assertTrue(actual2);
    }
    
    @Test
    public void testAddFavoriteLocationAndDeleteFavoriteLocation() {
        boolean actual1 = settings.addFavoriteLocation(username, location);
        assertTrue(actual1);
        
        boolean actual2 = settings.deleteDefaultLocation(username);
        assertTrue(actual2);
    }
    
    @Test
    public void testGetUserAPISubscriptions() {
        settings.addSubscription(username, "darksky");
        ArrayList<String> actual =  settings.getUserAPISubscriptions(username);
        int expectedSize = 1;
        int actualSize = actual.size();
        assertThat(actualSize, is(expectedSize));
        settings.deleteSubscriptions(username);
    }
    
    @Test
    public void testGetUserFavoriteLocation() {
        deleteFavoriteLocations(settings.getUserFavoriteLocation(username));
        settings.addFavoriteLocation(username, location);
        
        ArrayList<WeatherAPIGeoLocation> locations = 
                settings.getUserFavoriteLocation(username);
        int actual = locations.size();
        int expected = 1;
        assertThat(actual, is(expected));
    
        deleteFavoriteLocations(locations);
    }
    
    @Test
    public void testGetUserDefaultLocation() {
        settings.addDefaultLocation(username, location);
        WeatherAPIGeoLocation actual = settings.getUserDefaultLocation(username);
        assertThat(actual, is(notNullValue()));
        settings.deleteDefaultLocation(username);
    }
    
    /**
     * Also tests delete user profile and delete subscription.
     */
    @After
    public void teardown() {
        String username1 = "TestUser@testerz.com";
        boolean subscriptionDeleted = settings.deleteSubscriptions(username1);
        boolean deleted = profile.deleteUser(username1);
        assertTrue(subscriptionDeleted);
        assertTrue(deleted);
    }
    
    private void deleteFavoriteLocations(ArrayList<WeatherAPIGeoLocation> locations) {
        for(WeatherAPIGeoLocation location : locations) {
            settings.deleteFavoriteLocation(location.getLocationId());
        }
    }
}
  