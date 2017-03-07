package com.weatherApi.rest;

import java.util.ArrayList;
import java.util.List;

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
		String darkSkyKeyString = "e80440fb1812b94394324d93d488f300";
		
		WeatherAPIKey dSK = new WeatherAPIKey(darkSkyKeyString);
		DarkSkyAPICommand darkSky = new DarkSkyAPICommand();
		
		darkSky.setKey(dSK);
		
		List<WeatherAPICommand> commands = new ArrayList<WeatherAPICommand>();
		commands.add(darkSky);
		
		WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
		
		location.setLat(37.8267);
		location.setLongit(-122.4233);
		
		String response = darkSky.execute(darkSky.getKey(), location);
		return response;
	}
	
	@GET
	@Path("/openweathermapapi")
	public String getOpenWeatherMapAPIData() {
		String key = "995ae1c7e4573cf3578dd394ac69c532";
		
		WeatherAPIKey wkey = new WeatherAPIKey(key);
		OpenWeatherMapAPICommand openWeather = new OpenWeatherMapAPICommand();
				
		openWeather.setKey(wkey);
		
		List<WeatherAPICommand> commands = new ArrayList<WeatherAPICommand>();
		commands.add(openWeather);
		
		WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
		
		location.setLat(37.8267);
		location.setLongit(-122.4233);
		
		String response = openWeather.execute(openWeather.getKey(), location);
		if(response == null)
			return "The response is null";
		return response;
	}

	@GET
	@Path("/forecaapi")
	public String forecaAPIData() {
		String key = "KP1vQAGGx2Qx8adVWO83dqkvoqg";
		
		WeatherAPIKey wkey = new WeatherAPIKey(key);
		ForecaAPICommand forecaWeather = new ForecaAPICommand();
				
		forecaWeather.setKey(wkey);
		
		List<WeatherAPICommand> commands = new ArrayList<WeatherAPICommand>();
		commands.add(forecaWeather);
		
		WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
		
		location.setLat(37.8267);
		location.setLongit(-122.4233);
		
		String response = forecaWeather.execute(forecaWeather.getKey(), location);
		if(response == null)
			return "The response is null";
		return response;
	}
	
	@GET
	@Path("/yahooapi")
	public String yahooAPIData() {
		String key = "dj0yJmk9UnZTNVNFUUJDeEFGJmQ9WVdrOWExUjJkSHBQTldVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0zMQ--";
		
		WeatherAPIKey wkey = new WeatherAPIKey(key);
		YahooAPICommand yahooWeather = new YahooAPICommand();
				
		yahooWeather.setKey(wkey);
		
		List<WeatherAPICommand> commands = new ArrayList<WeatherAPICommand>();
		commands.add(yahooWeather);
		
		WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
		
		location.setLat(37.8267);
		location.setLongit(-122.4233);
		
		String response = yahooWeather.execute(yahooWeather.getKey(), location);
		if(response == null)
			return "The response is null";
		return response;
	}
}
