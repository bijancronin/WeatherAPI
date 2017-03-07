package com.weather.apiManager.command;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/*
 * This is the class for retrieving data from the weather.com website.  
 */

public class ForecaAPICommand implements WeatherAPICommand {

	private WeatherAPIKey key;
	
	@Override
	public String execute(WeatherAPIKey key, WeatherAPIGeoLocation location)  {
		
    
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

	@Override
	public String parseResponse(String JSONResponse) {
		JSONObject obj = new JSONObject(JSONResponse);
		JSONObject output = new JSONObject();
		
		return null;
	}
	
	public WeatherAPIKey getKey() {
		return key;
	}

	public void setKey(WeatherAPIKey key) {
		this.key = key;
	}
	

}
