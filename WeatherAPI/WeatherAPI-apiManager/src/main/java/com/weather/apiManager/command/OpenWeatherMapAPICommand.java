package com.weather.apiManager.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * This is the class for retrieving data from the openweathermap.org website.  
 */
public class OpenWeatherMapAPICommand implements WeatherAPICommand {

    private WeatherAPIKey key;
    private WeatherAPIGeoLocation location;
    private final String API_END_POINT = "http://api.openweathermap.org/data/2.5/forecast?";
    
    public OpenWeatherMapAPICommand(WeatherAPIKey key, WeatherAPIGeoLocation location) {
        this.key = key;
        this.location = location;
    }
	
    @Override
    public String execute()  {
    	
    	/*String openWmapUrl = "http://api.openweathermap.org/data/2.5/forecast?lat="+location.getLat()+"&lon="+
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
    				return null;*/
    				
    	APIResponsesDAO apiResponseDAO = new APIResponsesDAO();
        String json = apiResponseDAO.getAPIResponse(APIResponsesDAO.OPEN_WEATHER_MAP, location);
        
        if(json == null) {
            json = getJSON();
            APIResponseBean bean = new APIResponseBean();
            bean.setLatitude(location.getLat());
            bean.setLongitude(location.getLongit());
            bean.setApi(APIResponsesDAO.OPEN_WEATHER_MAP);
            bean.setJson(json);
            bean.setRequestTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            apiResponseDAO.addApiResponse(bean);
        }
        return json;  

    }
    
    private String getJSON() {
    	boolean nextDateReady = false;  
    	SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");    	
        Date curr_date = new Date();
        Date next_date = curr_date;        
    	
    	String latitude = String.valueOf(location.getLat());
    	String longitude = String.valueOf(location.getLongit());
        String fullQuery =
        API_END_POINT +
        "lat=" + latitude +
        "&lon=" + longitude +
        "&appid=" + key.getSecretKey();
        
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
            JSONArray listArray = hourlyObject.getJSONArray("list"); //select all the list of data
            
            json.append("\"hourly\" : {");
            json.append("\"summary\" : \"").append("Not Available").append("\",");
            json.append("\"icon\" : \"").append("Not Available").append("\",");
            json.append("\"data\" : [");                        
                        
            //we loop through our array and do some pretty printing of hourly weather information
            for(int i =0; (i < listArray.length() && daysBetween(curr_date, next_date) == 0 ); i++)             
            {
            	
                JSONObject data = (JSONObject) listArray.get(i);
                String preci = "Not Available";                
                next_date = dt1.parse(data.getString("dt_txt"));
                
                if(nextDateReady == false){
                	curr_date = dt1.parse(data.getString("dt_txt"));
                	nextDateReady = true;
                } 
                
                if(daysBetween(curr_date, next_date) != 0) {
                    json.append("}");
                    json.append("],"); 
                    break;
                }
                
                String weather_data = data.get("weather").toString();
                weather_data = weather_data.substring(1, weather_data.length());
                JSONObject weather = new JSONObject(weather_data);                                            	                
                
                json.append("{");
                json.append("\"time\" : \"").append(data.get("dt_txt")).append("\",");
                json.append("\"summary\" : \"").append(weather.get("description").toString()).append("\",");
                json.append("\"icon\" : \"").append(weather.get("icon").toString()).append("\",");                                
                
                if(data.get("rain") != null && data.has("rain")){     
                	String rain_st = data.get("rain").toString();                	
                	int idx1 = rain_st.indexOf(':');
                	int idx2 = rain_st.indexOf('}', idx1);
                	if(idx1 != -1 && idx2 != -1 && idx1 < idx2)
                		preci = rain_st.substring(idx1 + 1, idx2);
                	else
                		preci = "Not Available";                	
                }else if(data.get("snow") != null && data.has("snow")){
                	String snow_st = data.get("snow").toString();                	
                	int idx1 = snow_st.indexOf(':');
                	int idx2 = snow_st.indexOf('}', idx1);
                	if(idx1 != -1 && idx2 != -1 && idx1 < idx2)
                		preci = snow_st.substring(idx1 + 1, idx2);
                	else
                		preci = "Not Available";
                }                                
                
                json.append("\"precipitation\" : \"").append(preci).append("\",");
                json.append("\"precipitation_probability\" : \"").append("Not Available").append("\",");
                json.append("\"temperature\" : \"").append(data.getJSONObject("main").get("temp")).append("\",");
                json.append("\"real_feel_temperature\" : \"").append("Not Available").append("\",");
                json.append("\"humidity\" : \"").append(data.getJSONObject("main").get("humidity")).append("\",");
                json.append("\"wind_speed\" : \"").append(data.getJSONObject("wind").get("speed")).append("\",");
                json.append("\"wind_bearing\" : \"").append(data.getJSONObject("wind").get("deg")).append("\",");
                json.append("\"pressure\" : \"").append(data.getJSONObject("main").get("pressure")).append("\",");
                json.append("\"visibility\" : \"").append("Not Available").append("\"");
                                                           
                               
                json.append("},");               
                                
            }
            
            json.append("\"daily\" : [");
            next_date = curr_date; 
            boolean[] daily_check = {false, false, false, false, false};
            int count = 0;
            int old_time_diff = 0;
            // get the daily data
            for(int i =0; (i < listArray.length() && daysBetween(curr_date, next_date) < 5 ); i++)
            {
            	//we parse the contents to a our json object for processing                
            	JSONObject data = (JSONObject) listArray.get(i);
            	next_date = dt1.parse(data.getString("dt_txt"));                
                
                if(daysBetween(curr_date, next_date) == 5) {
                    json.append("}");
                    json.append("],"); 
                    break;
                }   
                
                if(daysBetween(curr_date, next_date) - old_time_diff > 0){
                	count ++;
                	old_time_diff = daysBetween(curr_date, next_date);
                }
                
                if(!daily_check[count]){
	                String weather_data = data.get("weather").toString();
	                weather_data = weather_data.substring(1, weather_data.length());
	                JSONObject weather = new JSONObject(weather_data); 
	                
	                JSONObject main_data = new JSONObject(data.get("main").toString()); 
	                    
	                json.append("{");                
	                json.append("\"time\" : \"").append(data.getString("dt_txt")).append("\",");
	                json.append("\"summary\" : \"").append(weather.get("description").toString()).append("\",");
	                json.append("\"min_temperature\" : \"").append(main_data.get("temp_min").toString()).append("\",");
	                json.append("\"max_temperature\" : \"").append(main_data.get("temp_max").toString()).append("\"");
	                
	                json.append("},");     
	                daily_check[count] = true;	                
                }
            }
            
            json.append("]");
            json.append("}");
            json.append("}"); 
            
                                                          
            
            
        } catch (MalformedURLException rat) {
            rat.printStackTrace(); //bad url or query
        } 
        catch (Exception rat) { //all other exceptions including io
            rat.printStackTrace();
        }        
        
        return json.toString();
    }
    
    private static int daysBetween(Date d1, Date d2){
    	return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
    
	private static Calendar dateToCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}
}
