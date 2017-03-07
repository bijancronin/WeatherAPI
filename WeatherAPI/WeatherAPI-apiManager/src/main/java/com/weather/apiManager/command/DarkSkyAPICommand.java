package com.weather.apiManager.command;

import org.json.*;

import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.DarkSkyClient;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.Latitude;
import tk.plogitech.darksky.forecast.Longitude;

public class DarkSkyAPICommand implements WeatherAPICommand{
	
	private WeatherAPIKey key;

	@Override
	public String execute(WeatherAPIKey key, WeatherAPIGeoLocation location) {
		
		ForecastRequest request = new ForecastRequestBuilder()
		        .key(new APIKey(key.getSecretKey()))
		        .exclude(ForecastRequestBuilder.Block.minutely)
		        .extendHourly()
		        .location(new GeoCoordinates(new Longitude(location.getLongit()), new Latitude(location.getLat()))).build();
		
		DarkSkyClient client = new DarkSkyClient();
		String forecast = null;
	    try {
			forecast = client.forecastJsonString(request);
		} catch (ForecastException e) {
			e.printStackTrace();
		}
		
		return forecast;
	}

	@Override
	public String parseResponse(String JSONResponse) {
		
		JSONObject obj = new JSONObject(JSONResponse);
		JSONObject output = new JSONObject();
		
		// parse only the set of attributes we decided on
		

		return null;
	}

	public WeatherAPIKey getKey() {
		return key;
	}

	public void setKey(WeatherAPIKey key) {
		this.key = key;
	}


}
