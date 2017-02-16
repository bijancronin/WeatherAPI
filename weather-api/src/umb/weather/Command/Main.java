package umb.weather.Command;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
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
		
		System.out.println(response);
		
	}

}
