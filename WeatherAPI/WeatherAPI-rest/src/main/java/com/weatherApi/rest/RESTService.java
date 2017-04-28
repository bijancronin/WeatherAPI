package com.weatherApi.rest;

import com.google.gson.Gson;
import com.weather.apiManager.command.AccuWeatherCommand;
import com.weather.apiManager.command.ApixuAPICommand;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.weather.apiManager.command.DarkSkyAPICommand;
import com.weather.apiManager.command.ForecaAPICommand;
import com.weather.apiManager.command.OpenWeatherMapAPICommand;
import com.weather.apiManager.command.WeatherAPICommand;
import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.weather.apiManager.command.WeatherAPIKey;
import com.weather.apiManager.command.WeatherBitCommand;
import com.weather.apiManager.command.WunderGroundCommand;
import com.weather.apiManager.command.YahooAPICommand;
import com.weather.apiManager.dl.APIResponsesDAO;
import com.weatherAPI.userProfileManager.UserSettings;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("/api")
public class RESTService {
	
    @GET
    @Path("/"+APIResponsesDAO.DARK_SKY+"/{username}")
    public String getDarkSkyAPIData(@PathParam("username") String username) {
        WeatherAPIKey key = new WeatherAPIKey("e80440fb1812b94394324d93d488f300");
        WeatherAPIGeoLocation location = getUserDefaultLocation(username);
        WeatherAPICommand darkSky = new DarkSkyAPICommand(key, location);
        String response = darkSky.execute();
        return response;
    }
    
    @GET
    @Path("/"+APIResponsesDAO.APIXU+"/{username}")
    public String getApixuAPIData(@PathParam("username") String username) {
        WeatherAPIKey key = new WeatherAPIKey("d882133b26e248fe91f192249171503");
        WeatherAPIGeoLocation location = getUserDefaultLocation(username);
        ApixuAPICommand command = new ApixuAPICommand(key, location);
        String response = command.execute();
        return response;
    }

    @GET
    @Path("/"+APIResponsesDAO.OPEN_WEATHER_MAP+"/{username}")
    public String getOpenWeatherMapAPIData(@PathParam("username") String username) {
        WeatherAPIKey key = new WeatherAPIKey("995ae1c7e4573cf3578dd394ac69c532");
        WeatherAPIGeoLocation location = getUserDefaultLocation(username);
        WeatherAPICommand openWeather = 
                new OpenWeatherMapAPICommand(key, location);
        String response = openWeather.execute();
        return response;
    }

    @GET
    @Path("/"+APIResponsesDAO.FORECA+"/{username}")
    public String forecaAPIData(@PathParam("username") String username) {
        WeatherAPIKey key = new WeatherAPIKey("KP1vQAGGx2Qx8adVWO83dqkvoqg");
        WeatherAPIGeoLocation location = getUserDefaultLocation(username);
        WeatherAPICommand forecaWeather = 
                new ForecaAPICommand(key, location);
        String response = forecaWeather.execute();
        return response;
    }

    @GET
    @Path("/"+APIResponsesDAO.ACCUWEATHER+"/{username}")
    public String accuWeatherAPIData(@PathParam("username") String username) {
        WeatherAPIKey key = new WeatherAPIKey("cO1CZkYq2A0xyjj6DF4WNzq9tGxmCPb0");
        WeatherAPIGeoLocation location = getUserDefaultLocation(username);
        WeatherAPICommand accuWeather = 
                new AccuWeatherCommand(key, location);
        String response = accuWeather.execute();
        return response;
    }
    
    @GET
    @Path("/"+APIResponsesDAO.WEATHERBIT+"/{username}")
    public String weatherBitAPIData(@PathParam("username") String username) {
        WeatherAPIKey key = new WeatherAPIKey("4551dc9c12b2405dbd6378c1737ed580");
        WeatherAPIGeoLocation location = getUserDefaultLocation(username);
        WeatherAPICommand weatherBit = 
                new WeatherBitCommand(key, location);
        String response = weatherBit.execute();
        return response;
    }
    
    @GET
    @Path("/"+APIResponsesDAO.WUNDER+"/{username}")
    public String wunderGroundAPIData(@PathParam("username") String username) {
        WeatherAPIKey key = new WeatherAPIKey("c03547405e706ce3");
        WeatherAPIGeoLocation location = getUserDefaultLocation(username);
        WeatherAPICommand wunderGround = 
                new WunderGroundCommand(key, location);
        String response = wunderGround.execute();
        return response;
    }
    
    @GET
    @Path("/getlocation/{username}")
    public String getUserLocation(@PathParam("username") String username) {
        Gson gson = new Gson();
        String jsonLocation = gson.toJson(getUserDefaultLocation(username));
        return jsonLocation;
    }
    
    private WeatherAPIGeoLocation getUserDefaultLocation(String username) {
        WeatherAPIGeoLocation defaultLocation;
        UserSettings settings = new UserSettings();
        defaultLocation = settings.getUserDefaultLocation(username);
        if(defaultLocation == null) {
            List<WeatherAPIGeoLocation> locations = 
                    settings.getUserFavoriteLocation(username);
            if(!locations.isEmpty()) {
                defaultLocation = locations.get(0);
            }
            else {
                defaultLocation = new WeatherAPIGeoLocation();
                defaultLocation.setLat(37.8267);
                defaultLocation.setLongit(-122.4233);
                defaultLocation.setCity("Boston");
                defaultLocation.setState("MA");
                defaultLocation.setCountry("US");
                defaultLocation.setZipcode("02125");
            }
        }
        return defaultLocation;
    }
}
