package com.weather.apiManager.command;

import java.util.List;

public class WeatherAPIManager {
	
	private String JSONResponse;
	
	// given a list of Command classes call each of those APIs and create a JSON with each of the parsed versions of it
	void fetchWeatherData(List<WeatherAPICommand> appKeys) {
		
	}

	public String getJSONResponse() {
		return JSONResponse;
	}

}
