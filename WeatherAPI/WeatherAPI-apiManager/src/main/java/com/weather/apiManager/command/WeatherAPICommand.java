package com.weather.apiManager.command;

/**
 * An interface which all API command classes need to implement.
 * Also, they need to have a constructor accepting an WeatherAPIKey
 * and WeatherAPIGeoLocation.
 */
public interface WeatherAPICommand {
    /**
     * This method tries to find the JSON for given latitude and longitude
     * in the database. If its not found in DB, it makes an API call and 
     * stores the response in the DB for next use. Remember : Each entry in DB
     * is valid for 1 hour.
     * @return Returns JSON either fetched from API call or from DB. 
     */
    String execute();
}
