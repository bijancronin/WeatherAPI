package com.weatherAPI.userProfileManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by HaeYoung on 4/2/2017.
 */
public class UserSettingsTest {
    UserSettings settings;
    UserProfile profile;

    @Before
    public void setup() {
        settings = new UserSettings();
        assertNotNull(settings);

        String username = "TestUser@testerz.com";
        String name = "Tester McTestTest";
        String password = "ITestStuff";
        profile = new UserProfile();
        profile.createUser(username, name, password);
    }

    @Test
    public void addKeyTest() {
        String username = "TestUser@testerz.com";
        String api = "yahoo";
        String key = "thisisalongstring1";

        boolean added = settings.addKey(username, api, key);

        assertTrue(added);
    }

    @Test
    public void addDefaultLocationTest() {
        String username = "TestUser@testerz.com";
        String location = "Boston,MA";

        boolean added = settings.addDefaultLocation(username, location);

        assertTrue(added);
    }

    @Test
    public void addFavoriteLocationTest() {
        String username = "TestUser@testerz.com";
        String location = "Boston,MA";

        boolean added = settings.addFavoriteLocation(username, location);

        assertTrue(added);
    }

    @Test
    public void updateKeyTest() {
        String username = "TestUser@testerz.com";
        String api = "yahoo";
        String key = "anotherlongstring2";

        boolean updated = settings.updateKey(username, api, key);

        assertTrue(updated);
    }

    @Test
    public void updateDefaultLocationTest() {
        String username = "TestUser@testerz.com";
        String location = "Burlington,MA";

        boolean updated = settings.updateDefaultLocation(username, location);

        assertTrue(updated);
    }

    @Test
    public void deleteKeyTest() {
        String username = "TestUser@testerz.com";
        String api = "yahoo";
        String key = "anotherlongstring2";

        boolean deleted = settings.deleteKey(username, api, key);

        assertTrue(deleted);
    }

    @Test
    public void deleteFavoriteLocationTest() {
        String username = "TestUser@testerz.com";
        String location = "Boston,MA";

        boolean deleted = settings.deleteFavoriteLocation(username, location);

        assertTrue(deleted);
    }


    @After
    public void teardown() {
        String username1 = "TestUser@testerz.com";

        boolean deleted = profile.deleteUser(username1);
        assertTrue(deleted);
    }

}
