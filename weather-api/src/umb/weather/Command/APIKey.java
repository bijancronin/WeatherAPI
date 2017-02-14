package umb.weather.Command;

public class APIKey {
	
	private String secretKey;
	
	APIKey(String key) {
		this.secretKey = key;
	}

	public String getSecretKey() {
		return secretKey;
	}

}
