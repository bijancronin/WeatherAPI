package umb.weather.Command;

import java.util.List;

public interface WeatherAPICommand {
	
	// run the API with the APIKey and get a JSON response
	String execute(APIKey key);
	
	// creste the request - usually at the base URL you add the APIKey string and the location
	// the location is sometimes as a lat/long thing or as a city/state
	// that's why we are using the List of Strings 
	String createTheQuery(List<String> vals);
	
	// parse the previous response into the standard form agreed upon
	String parseResponse(String JSONResponse);

}
