package com.weather.apiManager.command;

import com.weather.apiManager.dl.APIResponseBean;
import com.weather.apiManager.dl.APIResponsesDAO;
import com.weatherlibrary.datamodel.Current;
import com.weatherlibrary.datamodel.Forecastday;
import com.weatherlibrary.datamodel.Hour;
import com.weatherlibrary.datamodel.WeatherModel;
import com.weatherlibraryjava.IRepository;
import com.weatherlibraryjava.Repository;
import com.weatherlibraryjava.RequestBlocks;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * Future Note : We don't need to convert attributes explicitly.
 * APIXU provides this for us.
 */
public class ApixuAPICommand implements WeatherAPICommand {
    private WeatherAPIKey key;
    private WeatherAPIGeoLocation location;

    public ApixuAPICommand(WeatherAPIKey key, WeatherAPIGeoLocation location) {
        this.key = key;
        this.location = location;
    }
    
    /**
     * This method tries to find the JSON for given latitude and longitude
     * in the database. If its not found in DB, it makes an API call and 
     * stores the response in the DB for next use. Remember : Each entry in DB
     * is valid for 1 hour.
     * @return Returns JSON either fetched from API call or from DB. 
     */
    @Override
    public String execute() {
        APIResponsesDAO apiResponseDAO = new APIResponsesDAO();
        String json = apiResponseDAO.getAPIResponse(
                APIResponsesDAO.APIXU, location);
        if(json == null) {
            json = getJSON();
            APIResponseBean bean = new APIResponseBean();
            bean.setLatitude(location.getLat());
            bean.setLongitude(location.getLongit());
            bean.setApi(APIResponsesDAO.APIXU);
            bean.setJson(json);
            bean.setRequestTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            apiResponseDAO.addApiResponse(bean);
        }
        
        return json;
    }
    
    private String getJSON() {
        IRepository repository = new Repository();
        WeatherModel weatherModel = null;
        StringBuilder json = new StringBuilder(0);
        try {
            weatherModel =  repository.GetWeatherDataByLatLong(key.getSecretKey(), 
                    String.valueOf(location.getLat()), 
                    String.valueOf(location.getLongit()), 
                    RequestBlocks.Days.Seven);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // Check if the api call was successfully
        if(weatherModel != null) {
            json.append("{");
            
            json.append("\"latitude:\":\"")
                    .append(weatherModel.getLocation().getLat())
                    .append("\",");
            json.append("\"longitude:\":\"")
                    .append(weatherModel.getLocation().getLong())
                    .append("\",");
            
            Current current =  weatherModel.getCurrent();
            List<Hour> hourly = weatherModel.getForecast()
                    .getForecastday().get(0).getHour();
            
            json.append("\"hourly\":{");
            json.append("\"summary\":\"").append(current.getCondition().getText())
                    .append("\",");
            json.append("\"icon\":\"").append(current.getCondition().getIcon())
                    .append("\",");
            json.append("\"data\":[");
            
            for(int i=0; i<hourly.size(); i++) {
                Hour hour = hourly.get(i);
                json.append("{");
                json.append("\"time\":\"").append(hour.getTime())
                        .append("\",");
                json.append("\"summary\":\"").append(hour.getCondition().getText())
                        .append("\",");
                json.append("\"icon\":\"").append(hour.getCondition().getIcon())
                        .append("\",");
                json.append("\"precipitation\":\"").append(hour.getPrecipIn())
                        .append("\",");
                json.append("\"precipitation_probability\":\"").append(0)
                        .append("\",");
                json.append("\"temperature\":\"").append(hour.getTempF())
                        .append("\",");
                json.append("\"real_feel_temperature\":\"").append(hour.getFeelslikeF())
                        .append("\",");
                json.append("\"humidity\":\"").append(hour.getHumidity())
                        .append("\",");
                json.append("\"wind_speed\":\"").append(hour.getWindMph())
                        .append("\",");
                json.append("\"wind_bearing\":\"").append(hour.getWindDir())
                        .append("\",");
                json.append("\"pressure\":\"").append(hour.getPressureIn())
                        .append("\",");
                json.append("\"visibility\":\"").append("Not Available")
                        .append("\"");
                if(i == hourly.size()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            json.append("]},");
            
            json.append("\"daily\":[");
            
            List<Forecastday> daily = weatherModel.getForecast().getForecastday();
            
            for(int i=0; i<daily.size(); i++) {
                Forecastday forecast = daily.get(i);
                json.append("{");
                
                json.append("\"time\":\"").append(forecast.getDate())
                        .append("\",");
                json.append("\"summary\":\"").append(forecast.getDay()
                        .getCondition().getText())
                        .append("\",");
                json.append("\"min_temperature\":\"").append(forecast.getDay()
                        .getMintempF())
                        .append("\",");
                json.append("\"max_temperature\":\"").append(forecast.getDay()
                        .getMaxtempF())
                        .append("\"");
                if(i == daily.size()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            
            json.append("],");
            
            json.append("\"alerts\":[");
            json.append("]");
            json.append("}");
        }
        
        return json.toString();
    }
}
