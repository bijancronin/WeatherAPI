package com.weather.apiManager.command;

import com.weather.apiManager.dl.APIResponseBean;
import com.weather.apiManager.dl.APIResponsesDAO;
import java.sql.Timestamp;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


public class WunderGroundCommand implements WeatherAPICommand {
    
    private WeatherAPIKey key;
    private WeatherAPIGeoLocation location;
    private final String API_END_POINT = "http://api.wunderground.com/api/";
    
    public WunderGroundCommand(WeatherAPIKey key, WeatherAPIGeoLocation location) {
        this.key = key;
        this.location = location;
    }
    
    @Override
    public String execute() {
    	if(key.getSecretKey() == "invalid" || key.getSecretKey() == "" || key.getSecretKey() == null) return null;
    	
        APIResponsesDAO apiResponseDAO = new APIResponsesDAO();
        String json = apiResponseDAO.getAPIResponse(
                                                    APIResponsesDAO.WUNDER, location);
        if(json == null) {
            json = getJSON();
            APIResponseBean bean = new APIResponseBean();
            bean.setLatitude(location.getLat());
            bean.setLongitude(location.getLongit());
            bean.setApi(APIResponsesDAO.WUNDER);
            bean.setJson(json);
            bean.setRequestTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            apiResponseDAO.addApiResponse(bean);
        }
        
       return json; 
    }
    
    private String getJSON() {
        String city = location.getCity();
        String state = location.getState();
        String fullQuery =
        API_END_POINT +
        key.getSecretKey() +
        "/conditions/q/" +
        state + "/" + city +	".json";
        String hourly =
        API_END_POINT +
        key.getSecretKey() +
        "/hourly/q/" +
        state + "/" + city +	".json";
        String forecast =
        API_END_POINT +
        key.getSecretKey() +
        "/forecast/q/" +
        state + "/" + city +	".json";
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
            JSONObject jsonObject = new JSONObject(contents); //the root json object
            String cond = jsonObject.get("current_observation").toString();
            JSONObject obj = new JSONObject(cond);
            String  obsloc = obj.get("observation_location").toString();
            JSONObject obslocObj = new JSONObject(obsloc);
            
            
            //	        System.out.println(obslocObj.get("latitude").toString());
            
            json.append("{");
            json.append("\"latitude\" : \"").append(obslocObj.get("latitude").toString()).append("\",");
            json.append("\"longitude\" : \"").append(obslocObj.get("longitude").toString()).append("\",");
            
            
            
            //using the state & zip, we construct a new url, download it and stick its head into the inputStream
            URL hourlyUrl = new URL(hourly);
            connection = (HttpURLConnection) hourlyUrl.openConnection();
            inputStream = connection.getInputStream();
            
            //again, we suck the contents of the inputStream and stuff into a string for future use
            buffer = new BufferedReader(new InputStreamReader(inputStream));
            contents = "";
            while ( (line = buffer.readLine()) != null) {
                contents += "\n" + line;
            }
            
            //we parse the contents to a our json object for processing
            JSONObject hourlyObject = new JSONObject(contents); //root json object
            JSONArray forecastArray = hourlyObject.getJSONArray("hourly_forecast"); //specific one we need
            
            json.append("\"hourly\" : {");
            json.append("\"summary\" : \"").append("Not Available").append("\",");
            json.append("\"icon\" : \"").append("Not Available").append("\",");
            json.append("\"data\" : [");
            
            
            //we loop through our array and do some pretty printing of hourly weather information
            for(int i =0; i < forecastArray.length(); i++) {
                JSONObject hour = (JSONObject) forecastArray.get(i);
                
                json.append("{");
                json.append("\"time\" : \"").append(hour.getJSONObject("FCTTIME").get("pretty")).append("\",");
                json.append("\"summary\" : \"").append("Not available").append("\",");
                json.append("\"icon\" : \"").append(hour.get("icon")).append("\",");
                json.append("\"precipitation\" : \"").append(hour.get("condition")).append("\",");
                json.append("\"precipitation_probability\" : \"").append(hour.get("pop")).append("\",");
                json.append("\"temperature\" : \"").append(hour.getJSONObject("temp").get("english")).append("\",");
                json.append("\"real_feel_temperature\" : \"").append(hour.getJSONObject("feelslike").get("english")).append("\",");
                json.append("\"humidity\" : \"").append(hour.get("humidity")).append("\",");
                json.append("\"wind_speed\" : \"").append(hour.getJSONObject("wspd").get("english")).append("\",");
                json.append("\"wind_bearing\" : \"").append(hour.getJSONObject("wdir").get("dir")).append("\",");
                json.append("\"pressure\" : \"").append(hour.getJSONObject("mslp").get("english")).append("\",");
                json.append("\"visibility\" : \"").append(hour.get("sky")).append("\"");
                
                
                
                //	            System.out.printf("\n\n%s \n\t condition: %s \n\t temperature: %sF \n\t feels like: %sF \n\t snow: %s \n\t relative humidity: %s ",
                //	                    hour.getJSONObject("FCTTIME").get("pretty"),
                //	                    hour.get("condition"),
                //	                    hour.getJSONObject("temp").get("english"),
                //	                    hour.getJSONObject("feelslike").get("english"),
                //	                    hour.getJSONObject("snow").get("english"),
                //	                    hour.get("humidity"));
                
                if(i == forecastArray.length()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            
            json.append("]},");
            
            //using the state & zip, we construct a new url, download it and stick its head into the inputStream
            URL forecastUrl = new URL(forecast);
            connection = (HttpURLConnection) forecastUrl.openConnection();
            inputStream = connection.getInputStream();
            
            //again, we suck the contents of the inputStream and stuff into a string for future use
            buffer = new BufferedReader(new InputStreamReader(inputStream));
            contents = "";
            while ( (line = buffer.readLine()) != null) {
                contents += "\n" + line;
            }
            
            //we parse the contents to a our json object for processing
            
            JSONObject forecastObject = new JSONObject(contents); //root json object
            JSONObject fr = new JSONObject(forecastObject.get("forecast").toString());
            JSONObject simpleForecast = new JSONObject(fr.get("simpleforecast").toString()); //specific one we need
            
            
            
            JSONArray forecastDayArray = simpleForecast.getJSONArray("forecastday"); //specific one we
            
            json.append("\"daily\" : [");
            JSONObject timeObj;
            
            for(int i=0; i<forecastDayArray.length(); i++) {
                JSONObject day = (JSONObject) forecastDayArray.get(i);
                
                json.append("{");
                timeObj = new JSONObject(day.get("date").toString());
                json.append("\"time\" : \"").append(timeObj.get("pretty")).append("\",");
                json.append("\"summary\" : \"").append(day.get("conditions")).append("\",");
                json.append("\"min_temperature\" : \"").append(day.getJSONObject("low").get("fahrenheit")).append("\",");
                json.append("\"max_temperature\" : \"").append(day.getJSONObject("high").get("fahrenheit")).append("\"");
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
    
}


