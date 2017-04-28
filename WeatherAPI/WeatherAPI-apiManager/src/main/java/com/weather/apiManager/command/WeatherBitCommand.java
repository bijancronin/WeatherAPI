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


/**
 *
 * This only offers forecasts every 3hours, and no daily forecasts
 * For daily forecasts the API should get an account with Basic Plan (which is like $35/month)
 *
 */

public class WeatherBitCommand implements WeatherAPICommand {
    
    private WeatherAPIKey key;
    private WeatherAPIGeoLocation location;
    
    public WeatherBitCommand(WeatherAPIKey key, WeatherAPIGeoLocation location) {
        this.key = key;
        this.location = location;
    }
    
    @Override
    public String execute() {
        APIResponsesDAO apiResponseDAO = new APIResponsesDAO();
        String json = apiResponseDAO.getAPIResponse(
                                                    APIResponsesDAO.WEATHERBIT, location);
        if(json == null) {
            json = getJSON();
            APIResponseBean bean = new APIResponseBean();
            bean.setLatitude(location.getLat());
            bean.setLongitude(location.getLongit());
            bean.setApi(APIResponsesDAO.WEATHERBIT);
            bean.setJson(json);
            bean.setRequestTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            apiResponseDAO.addApiResponse(bean);
        }
        
       return json; 
    }
    
    private String getJSON() {
        String city = location.getCity();
        String country = location.getCountry();
        String hourly = "https://api.weatherbit.io/v1.0/forecast/3hourly/geosearch?city=" + city + "&country=" + country + "&units=I&lang=uk&key=" + key.getSecretKey();
        
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
            JSONObject jsonObject = new JSONObject(contents);
            JSONArray forecastArray = jsonObject.getJSONArray("data");
            JSONObject sumobj;
            
            json.append("\"hourly\" : {");
            json.append("\"summary\" : \"").append("Not Available").append("\",");
            json.append("\"icon\" : \"").append("Not Available").append("\",");
            json.append("\"data\" : [");
            
            //we loop through our array and do some pretty printing of hourly weather information
            for(int i =0; i < forecastArray.length(); i++) {
                JSONObject hour = (JSONObject) forecastArray.get(i);
                
                json.append("{");
                json.append("\"time\" : \"").append(hour.get("datetime")).append("\",");
                sumobj = new JSONObject(hour.get("weather").toString());
                json.append("\"summary\" : \"").append(sumobj.get("description")).append("\",");
                json.append("\"icon\" : \"").append(sumobj.get("description")).append("\",");
                json.append("\"precipitation\" : \"").append(hour.get("precip")).append("\",");
                json.append("\"precipitation_probability\" : \"").append(hour.get("pop")).append("\",");
                json.append("\"temperature\" : \"").append(hour.get("app_temp")).append("\",");
                json.append("\"real_feel_temperature\" : \"").append(hour.get("temp")).append("\",");
                json.append("\"humidity\" : \"").append(hour.get("rh")).append("\",");
                json.append("\"wind_speed\" : \"").append(hour.get("wind_spd")).append("\",");
                json.append("\"wind_bearing\" : \"").append(hour.get("wind_cdir")).append("\",");
                json.append("\"pressure\" : \"").append(hour.get("pres")).append("\",");
                json.append("\"visibility\" : \"").append(hour.get("vis")).append("\"");
                
                
                if(i == forecastArray.length()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            
            json.append("]},");
            
            json.append("\"daily\" : [");
            json.append("],");
            
            json.append("\"alerts\" : []");
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


