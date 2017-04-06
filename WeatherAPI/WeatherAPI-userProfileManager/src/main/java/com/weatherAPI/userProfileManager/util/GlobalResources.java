package com.weatherAPI.userProfileManager.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A class for managing database connection.
 */
public class GlobalResources {
    
    /**
     * A static method for creating connection based on the username,
     * password, and db URL mentioned in the properties files.
     * @return Connection if successful else return null
     */
    public static Connection getConnection() {
        Properties properties = new Properties();
        InputStream input = null;
        Connection connection = null;
        
        try {
            input = GlobalResources.class.getResourceAsStream("/config.properties");
            properties.load(input);
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    properties.getProperty("dburl"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
        } catch(SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return connection;
    }
}
