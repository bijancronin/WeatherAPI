package umb.weather.Command;


public interface WeatherAPICommand {
	
	// run the API with the APIKey and get a JSON response
	String execute(WeatherAPIKey key, WeatherAPIGeoLocation location);
	
	// parse the previous response into the standard form agreed upon
	String parseResponse(String JSONResponse);

}
