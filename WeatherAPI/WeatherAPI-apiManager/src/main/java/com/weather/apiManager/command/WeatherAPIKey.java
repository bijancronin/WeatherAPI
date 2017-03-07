package com.weather.apiManager.command;

public class WeatherAPIKey {
	
	private String secretKey;
	
	public WeatherAPIKey(String key) {
		this.secretKey = key;
	}

	public String getSecretKey() {
		return secretKey;
	}

}
