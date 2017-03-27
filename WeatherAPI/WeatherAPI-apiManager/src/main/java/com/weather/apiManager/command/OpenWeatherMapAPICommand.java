package com.weather.apiManager.command;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/*
 * This is the class for retrieving data from the openweathermap.org website.  
 */
public class OpenWeatherMapAPICommand implements WeatherAPICommand {

    private WeatherAPIKey key;
    private WeatherAPIGeoLocation location;

    public OpenWeatherMapAPICommand(WeatherAPIKey key, WeatherAPIGeoLocation location) {
        this.key = key;
        this.location = location;
    }
	
    @Override
    public String execute()  {
            String openWmapUrl = "http://api.openweathermap.org/data/2.5/weather?lat="+location.getLat()+"&lon="+
    location.getLongit()+"&appid="+key.getSecretKey();		
            try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = httpclient.execute(new HttpGet(openWmapUrl));
                    HttpEntity entity = response.getEntity();
                    String responseString = EntityUtils.toString(entity, "UTF-8");
                    return responseString;
            } catch ( IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
            return null;


    }
}
