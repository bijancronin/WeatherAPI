package com.weatherAPI.userProfileManager;

import org.hamcrest.Matchers;
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

    @Before
    public void setup() {
        profile = new UserProfile();
        assertNotNull(profile);
    }

    @Test
    public void createUserTest() {
        String username = "TestUser@testerz.com";
        String name = "Tester McTestTest";
        String password = "ITestStuff";

        boolean created = profile.createUser(username, name, password);

        assertTrue(created);
    }

    @Test
    public void updateUserTest() {
        createUserTest();
        String username = "TestUser@testerz.com";
        String updatedName = "I_SHALL_NOT_TEST!";
        String password = "IDoNotTestStuff";
        boolean updated = profile.updateUser(updatedName, username, password);

        assertTrue(updated);
    }

    @Test
    public void authenticateUserTest() {
        createUserTest();
        String username = "TestUser@testerz.com";
        String password = "ITestStuff";

        boolean exist = (profile.authenticateUser(username, password) != null);
        
        assertTrue(exist);
    }

    @Test
    public void checkUsernameTest() {
        createUserTest();
        String username = "TestUser@testerz.com";

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
        String username1 = "TestUser@testerz.com";

        boolean deleted = profile.deleteUser(username1);
        assertTrue(deleted);
    }

}
