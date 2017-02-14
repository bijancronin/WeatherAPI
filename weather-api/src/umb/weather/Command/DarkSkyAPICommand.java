package umb.weather.Command;

import java.util.List;

public class DarkSkyAPICommand implements WeatherAPICommand{
	
	private APIKey key;

	@Override
	public String execute(APIKey key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String parseResponse(String JSONResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	public APIKey getKey() {
		return key;
	}

	public void setKey(APIKey key) {
		this.key = key;
	}

	@Override
	public String createTheQuery(List<String> vals) {
		String req = "https://api.darksky.net/forecast/";
		//"e80440fb1812b94394324d93d488f300/37.8267,-122.4233"
		
		for (String string : vals) {
			req = req + string + "/";
		}
		
		return req;
	}

}
