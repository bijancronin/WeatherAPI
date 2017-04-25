package com.weather.apiManager.util;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;

import java.sql.Connection;
import org.junit.Test;
import static org.junit.Assert.*;

public class GlobalResourcesTest {
    
    @Test
    public void testGetConnection() {
        Connection connection = GlobalResources.getConnection();
        assertThat(connection, is(notNullValue()));
    }
}
