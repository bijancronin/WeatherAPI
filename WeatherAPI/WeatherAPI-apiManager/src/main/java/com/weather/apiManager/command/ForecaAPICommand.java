package com.weather.apiManager.command;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/*
 * This is the class for retrieving data from the weather.com website.  
 */

public class ForecaAPICommand implements WeatherAPICommand {

    private WeatherAPIKey key;
    private WeatherAPIGeoLocation location;

    public ForecaAPICommand(WeatherAPIKey key, WeatherAPIGeoLocation location) {
        this.key = key;
        this.location = location;
    }

    @Override
    public String execute()  {


            String url = "http://apitest.foreca.net/?lon="+location.getLongit()+"&lat="+location.getLat()+"&key="
                            +key.getSecretKey()+"&format=json";

            try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = httpclient.execute(new HttpGet(url));
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
