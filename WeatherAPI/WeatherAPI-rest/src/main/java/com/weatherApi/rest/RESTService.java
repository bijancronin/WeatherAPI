package com.weatherApi.rest;

import com.weather.apiManager.command.ApixuAPICommand;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.weather.apiManager.command.DarkSkyAPICommand;
import com.weather.apiManager.command.ForecaAPICommand;
import com.weather.apiManager.command.OpenWeatherMapAPICommand;
import com.weather.apiManager.command.WeatherAPICommand;
import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.weather.apiManager.command.WeatherAPIKey;
import com.weather.apiManager.command.YahooAPICommand;

@Path("/api")
public class RESTService {
	
    @GET
    @Path("/darkskyapi")
    public String getDarkSkyAPIData() {
        WeatherAPIKey key = new WeatherAPIKey("e80440fb1812b94394324d93d488f300");
        WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
        location.setLat(37.8267);
                location.setLongit(-122.4233);
        WeatherAPICommand darkSky = new DarkSkyAPICommand(key, location);
        String response = darkSky.execute();
        return response;
    }
    
    @GET
    @Path("/apixu")
    public String getApixuAPIData() {
        WeatherAPIKey key = new WeatherAPIKey("d882133b26e248fe91f192249171503");
        WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
        location.setLat(37.8267);
		location.setLongit(-122.4233);
        ApixuAPICommand command = new ApixuAPICommand(key, location);
        String response = command.execute();
        return response;
    }

    @GET
    @Path("/openweathermapapi")
    public String getOpenWeatherMapAPIData() {
        WeatherAPIKey key = new WeatherAPIKey("995ae1c7e4573cf3578dd394ac69c532");
        WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
        location.setLat(37.8267);
                location.setLongit(-122.4233);
        WeatherAPICommand openWeather = 
                new OpenWeatherMapAPICommand(key, location);
        String response = openWeather.execute();
        return response;
    }

    @GET
    @Path("/forecaapi")
    public String forecaAPIData() {
        WeatherAPIKey key = new WeatherAPIKey("KP1vQAGGx2Qx8adVWO83dqkvoqg");
        WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
        location.setLat(37.8267);
                location.setLongit(-122.4233);
        WeatherAPICommand forecaWeather = 
                new ForecaAPICommand(key, location);
        String response = forecaWeather.execute();
        return response;
    }

    @GET
    @Path("/yahooapi")
    public String yahooAPIData() {
        WeatherAPIKey key = new WeatherAPIKey("dj0yJmk9UnZTNVNFUUJDeEFGJmQ9WVdrOWExUjJkSHBQTldVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0zMQ--");
        WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
        location.setLat(37.8267);
                location.setLongit(-122.4233);
        WeatherAPICommand yahooWeather = 
                new YahooAPICommand(key, location);
        String response = yahooWeather.execute();
        return response;
    }
}
