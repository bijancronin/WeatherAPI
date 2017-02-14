package umb.weather.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DarkSkyAPICommand implements WeatherAPICommand{
	
	private APIKey key;

	@Override
	public String execute(String req) {
		String output = null;
		
		try {

            URL url = new URL(req);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return output;
	}

	@Override
	public String parseResponse(String JSONResponse) {
		
		

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
