package umb.weather.Command;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		String darkSkyKeyString = "e80440fb1812b94394324d93d488f300";
		
		APIKey dSK = new APIKey(darkSkyKeyString);
		DarkSkyAPICommand darkSky = new DarkSkyAPICommand();
		
		darkSky.setKey(dSK);
		
		List<WeatherAPICommand> commands = new ArrayList<WeatherAPICommand>();
		commands.add(darkSky);
		
		
		
	}

}
