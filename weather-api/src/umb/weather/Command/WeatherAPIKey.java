package umb.weather.Command;

public class WeatherAPIKey {
	
	private String secretKey;
	
	WeatherAPIKey(String key) {
		this.secretKey = key;
	}

	public String getSecretKey() {
		return secretKey;
	}

}
