package com.weatherAPI.userProfileManager;

import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by HaeYoung on 4/2/2017.
 */
public class UserProfileTest {

    UserProfile profile;
    String username;
    String name;
    String password;

    @Before
    public void setup() {
        profile = new UserProfile();
        assertNotNull(profile);

        username = "TestUser@gmail.com";
        name = "UserOne";
        password = "Testing";
    }

    @Test
    public void createUserTest() {
        boolean created = profile.createUser(username, name, password);
        assertTrue(created);
    }

    @Test
    public void updateUserTest() {
        createUserTest();

        String updatedName = "UserTwo";

        boolean updated = profile.updateUser(updatedName, username, password);

        assertTrue(updated);
    }

    @Test
    public void authenticateUserTest() {
        createUserTest();

        boolean exist = (profile.authenticateUser(username, password) != null);
        
        assertTrue(exist);
    }

    @Test
    public void checkUsernameTest() {
        createUserTest();

        boolean exist = profile.checkUsernameExists(username);

        assertTrue(exist);
    }
    
    @Test
    public void testGetUserDetailsByUsernameSuccess() {
        profile.createUser("abc@abc.com", "abc", "abc");
        
        UserProfileBean user = profile
                .getUserDetailsByUsername("abc@abc.com");
        assertThat(user, is(notNullValue()));
        
        profile.deleteUser("abc@abc.com");
    }
    
    @Test
    public void testGetUserDetailsByUsernameFailure() {
        UserProfileBean user = profile
                .getUserDetailsByUsername("asbdjasdhj");
        assertThat(user, is(nullValue()));
    }

    @After
    public void teardown() {
        boolean deleted = profile.deleteUser(username);
        assertTrue(deleted);
    }

}
