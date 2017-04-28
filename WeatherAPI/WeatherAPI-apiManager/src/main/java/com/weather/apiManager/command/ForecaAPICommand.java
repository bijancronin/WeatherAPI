package com.weather.apiManager.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.weather.apiManager.dl.APIResponseBean;
import com.weather.apiManager.dl.APIResponsesDAO;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;

/*
 * This is the class for retrieving data from the foreca.net website.  
 */

public class ForecaAPICommand implements WeatherAPICommand {

    private WeatherAPIKey key;
    private WeatherAPIGeoLocation location;
    private final String API_END_POINT = "http://apitest.foreca.net/?";
    static HashMap<String, String> cloud_tbl = new HashMap<String, String>();
    static HashMap<String, String> preci_rate_tbl = new HashMap<String, String>();
    static HashMap<String, String> preci_type_tbl = new HashMap<String, String>();

    public ForecaAPICommand(WeatherAPIKey key, WeatherAPIGeoLocation location) {
        this.key = key;
        this.location = location;
        
        cloud_tbl.put("0","clear");
        cloud_tbl.put("1","almost clear");
        cloud_tbl.put("2","half cloudy");
        cloud_tbl.put("3","broken");
        cloud_tbl.put("4","overcast");
        cloud_tbl.put("5","thin high clouds");
        cloud_tbl.put("6","fog");
        
        preci_rate_tbl.put("0", "no precipitation");
        preci_rate_tbl.put("1", "slight precipitation");
        preci_rate_tbl.put("2", "showers");
        preci_rate_tbl.put("3", "precipitation");
        
        preci_type_tbl.put("0", "rain");
        preci_type_tbl.put("1", "sleet");
        preci_type_tbl.put("2", "snow");
        
    }

    @Override
    public String execute()  {

    	APIResponsesDAO apiResponseDAO = new APIResponsesDAO();
        String json = apiResponseDAO.getAPIResponse(
                                                    APIResponsesDAO.FORECA, location);
        if(json == null) {
            json = getJSON();
            APIResponseBean bean = new APIResponseBean();
            bean.setLatitude(location.getLat());
            bean.setLongitude(location.getLongit());
            bean.setCity(location.getCity());
            bean.setState(location.getState());
            bean.setCountry(location.getCountry());
            bean.setApi(APIResponsesDAO.FORECA);
            bean.setJson(json);
            bean.setRequestTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            apiResponseDAO.addApiResponse(bean);
        }
        return json;

    }
    
    private String getJSON() {
    	String latitude = String.valueOf(location.getLat());
    	String longitude = String.valueOf(location.getLongit());
        String fullQuery =
        API_END_POINT +
        "lon=" + longitude +
        "&lat=" + latitude +
        "&key=" + key.getSecretKey() 
        + "&format=json";
        
        StringBuilder json = new StringBuilder(0);
                
        
        try {
            //we'll get the json data from the url (using autoip) and stick its head into the inputSteam for reading
            URL url = new URL(fullQuery);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            
            //we'll then read from the inputStream and stuff into a string called contents
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            String contents = "";
            String line = "";
            while( (line = buffer.readLine()) != null) {
                contents += line; //populate $contents from the buffer
            }
                       
            //we parse the contents of the string to a json object for processing                                                            
            json.append("{");
            json.append("\"latitude\" : \"").append(latitude).append("\",");
            json.append("\"longitude\" : \"").append(longitude).append("\",");
                                               
            
            //we parse the contents to a our json object for processing
            JSONObject hourlyObject = new JSONObject(contents); //root json object
            JSONArray forecastArray = hourlyObject.getJSONArray("fch"); //select Hourly forecast (fch)
            
            json.append("\"hourly\" : {");
            json.append("\"summary\" : \"").append("Not Available").append("\",");
            json.append("\"icon\" : \"").append("Not Available").append("\",");
            json.append("\"data\" : [");                        
                        
            //we loop through our array and do some pretty printing of hourly weather information
            for(int i =0; i < forecastArray.length(); i++) {
                JSONObject hour = (JSONObject) forecastArray.get(i);
                
                json.append("{");
                json.append("\"time\" : \"").append(hour.get("dt")).append("\",");
                json.append("\"summary\" : \"").append(getSummary(hour.getString("s"))).append("\",");
                json.append("\"icon\" : \"").append("Not Available").append("\",");
                json.append("\"precipitation\" : \"").append(hour.get("p")).append("\",");
                json.append("\"precipitation_probability\" : \"").append("Not Available").append("\",");
                json.append("\"temperature\" : \"").append(hour.get("t")).append("\",");
                json.append("\"real_feel_temperature\" : \"").append(hour.get("tf")).append("\",");
                json.append("\"humidity\" : \"").append(hour.get("rh")).append("\",");
                json.append("\"wind_speed\" : \"").append(hour.get("ws")).append("\",");
                json.append("\"wind_bearing\" : \"").append(hour.get("wn")).append("\",");
                json.append("\"pressure\" : \"").append(hour.get("pr")).append("\",");
                json.append("\"visibility\" : \"").append(hour.get("v")).append("\"");
                                                             
                if(i == forecastArray.length()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            
            json.append("]},");
            
                       
            //we parse the contents to a our json object for processing
            
            JSONObject forecastObject = new JSONObject(contents); //root json object            
            JSONArray forecastDayArray = forecastObject.getJSONArray("fcd"); //Daily forecast (fcd)
            
            json.append("\"daily\" : [");
            JSONObject timeObj;
            
            for(int i=0; i<forecastDayArray.length(); i++) {
                JSONObject day = (JSONObject) forecastDayArray.get(i);
                
                json.append("{");                
                json.append("\"time\" : \"").append(day.get("dt")).append("\",");
                json.append("\"summary\" : \"").append(getSummary(day.getString("s"))).append("\",");
                json.append("\"min_temperature\" : \"").append(day.get("tn")).append("\",");
                json.append("\"max_temperature\" : \"").append(day.get("tx")).append("\"");
                if(i == forecastDayArray.length()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            json.append("]");
            json.append("}");
        } catch (MalformedURLException rat) {
            rat.printStackTrace(); //bad url or query
        } 
        catch (Exception rat) { //all other exceptions including io
            rat.printStackTrace();
        }        
        
        return json.toString();
    }
    
    private String getSummary(String code){
  	String summary = "";
  	
  	//ex: d421
  	if(code.length() == 4){
  		String daynight = code.substring(0,1);
  		String cloud_code = code.substring(1, 2);
  		String perci_rate_code = code.substring(2, 3);
  		String perci_type_code = code.substring(3, 4);                      
  		
  		if(daynight == "d") summary += "daytime, ";
  		else summary += "nighttime, ";
  		
  		summary += cloud_tbl.get(cloud_code);
  		if(!perci_rate_code.equals("0")){
  			summary += (", " + preci_rate_tbl.get(perci_rate_code) + ", ");
  			summary += preci_type_tbl.get(perci_type_code);
  		}
  		
  	}
  	
  	return summary;
  }
    
}
