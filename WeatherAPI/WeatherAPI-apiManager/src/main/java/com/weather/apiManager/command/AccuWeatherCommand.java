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


public class AccuWeatherCommand implements WeatherAPICommand {
    
    private WeatherAPIKey key;
    private WeatherAPIGeoLocation location;
    private final String API_END_POINT = "http://api.wunderground.com/api/";
    
    public AccuWeatherCommand(WeatherAPIKey key, WeatherAPIGeoLocation location) {
        this.key = key;
        this.location = location;
    }
    
    @Override
    public String execute() {
        APIResponsesDAO apiResponseDAO = new APIResponsesDAO();
        String json = apiResponseDAO.getAPIResponse(
                                                    APIResponsesDAO.ACCUWEATHER, location);
        if(json == null) {
            json = getJSON();
            APIResponseBean bean = new APIResponseBean();
            bean.setLatitude(location.getLat());
            bean.setLongitude(location.getLongit());
            bean.setApi(APIResponsesDAO.ACCUWEATHER);
            bean.setJson(json);
            bean.setRequestTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            apiResponseDAO.addApiResponse(bean);
        }
        
       return json; 
    }
    
    private String getJSON() {
        String hourly = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/" + location.getZipcode() + "?apikey=" + key.getSecretKey() + "&details=true";
        
        
        String forecast = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/" +  location.getZipcode() +"?apikey=" + key.getSecretKey() + "&details=true";
        String alerts = "http://dataservice.accuweather.com/alarms/v1/5day/" + location.getZipcode() + "?apikey=" + key.getSecretKey();
        
        StringBuilder json = new StringBuilder(0);
        
        try {
            
            json.append("{");
            json.append("\"latitude\" : \"").append(location.getLat()).append("\",");
            json.append("\"longitude\" : \"").append(location.getLongit()).append("\",");
            
            //using the state & zip, we construct a new url, download it and stick its head into the inputStream
            URL hourlyUrl = new URL(hourly);
            HttpURLConnection connection = (HttpURLConnection) hourlyUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            
            //again, we suck the contents of the inputStream and stuff into a string for future use
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            String contents = "";
            String line = "";
            while ( (line = buffer.readLine()) != null) {
                contents += "\n" + line;
            }
            
            //we parse the contents to a our json object for processing
            JSONArray forecastArray = new JSONArray(contents);
            
            json.append("\"hourly\" : {");
            json.append("\"summary\" : \"").append("Not Available").append("\",");
            json.append("\"icon\" : \"").append("Not Available").append("\",");
            json.append("\"data\" : [");
            
            //we loop through our array and do some pretty printing of hourly weather information
            for(int i =0; i < forecastArray.length(); i++) {
                JSONObject hour = (JSONObject) forecastArray.get(i);
                
                json.append("{");
                json.append("\"time\" : \"").append(hour.get("DateTime")).append("\",");
                json.append("\"summary\" : \"").append(hour.get("IconPhrase")).append("\",");
                json.append("\"icon\" : \"").append(hour.get("IconPhrase")).append("\",");
                json.append("\"precipitation\" : \"").append(hour.getJSONObject("TotalLiquid").get("Value")).append("\",");
                json.append("\"precipitation_probability\" : \"").append(hour.get("PrecipitationProbability")).append("\",");
                json.append("\"temperature\" : \"").append(hour.getJSONObject("Temperature").get("Value")).append("\",");
                json.append("\"real_feel_temperature\" : \"").append(hour.getJSONObject("RealFeelTemperature").get("Value")).append("\",");
                json.append("\"humidity\" : \"").append(hour.get("RelativeHumidity")).append("\",");
                json.append("\"wind_speed\" : \"").append(hour.getJSONObject("Wind").getJSONObject("Speed").get("Value")).append("\",");
                json.append("\"wind_bearing\" : \"").append(hour.getJSONObject("Wind").getJSONObject("Direction").get("Localized")).append("\",");
                json.append("\"pressure\" : \"").append("Not Available").append("\",");
                json.append("\"visibility\" : \"").append(hour.getJSONObject("Visibility").get("Value")).append("\"");
                
                
                if(i == forecastArray.length()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            
            json.append("]},");
            
            //we'll get the json data from the url (using autoip) and stick its head into the inputSteam for reading
            URL url = new URL(forecast);
            connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            
            //we'll then read from the inputStream and stuff into a string called contents
            buffer = new BufferedReader(new InputStreamReader(inputStream));
            contents = "";
            line = "";
            while( (line = buffer.readLine()) != null) {
                contents += line; //populate $contents from the buffer
            }
            
            //we parse the contents of the string to a json object for processing
            JSONObject jsonObject = new JSONObject(contents); //the root json object
            
            //we parse the contents to a our json object for processing
            
            JSONArray forecastDayArray = jsonObject.getJSONArray("DailyForecasts"); //specific one we
            
            json.append("\"daily\" : [");
            JSONObject summaryObj, tempObj;
            
            for(int i=0; i<forecastDayArray.length(); i++) {
                JSONObject day = (JSONObject) forecastDayArray.get(i);
                
                json.append("{");
                json.append("\"time\" : \"").append(day.get("Date")).append("\",");
                summaryObj = new JSONObject(day.get("Day").toString());
                json.append("\"summary\" : \"").append(summaryObj.get("LongPhrase")).append("\",");
                tempObj = new JSONObject(day.get("Temperature").toString());
                json.append("\"min_temperature\" : \"").append(tempObj.getJSONObject("Minimum").get("Value")).append("\",");
                json.append("\"max_temperature\" : \"").append(tempObj.getJSONObject("Maximum").get("Value")).append("\"");
                if(i == forecastDayArray.length()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            json.append("],");
            
            URL alertsURL = new URL(alerts);
            connection = (HttpURLConnection) alertsURL.openConnection();
            inputStream = connection.getInputStream();
            
            //again, we suck the contents of the inputStream and stuff into a string for future use
            buffer = new BufferedReader(new InputStreamReader(inputStream));
            contents = "";
            line = "";
            while ( (line = buffer.readLine()) != null) {
                contents += "\n" + line;
            }
            
            json.append("\"alerts\" : ").append(contents);
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


