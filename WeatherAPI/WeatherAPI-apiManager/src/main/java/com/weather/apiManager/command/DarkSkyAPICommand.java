package com.weather.apiManager.command;

import java.util.List;
import org.json.*;
import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;

import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.Latitude;
import tk.plogitech.darksky.forecast.Longitude;
import tk.plogitech.darksky.forecast.model.Alert;
import tk.plogitech.darksky.forecast.model.DailyDataPoint;
import tk.plogitech.darksky.forecast.model.DataPoint;
import tk.plogitech.darksky.forecast.model.Forecast;

public class DarkSkyAPICommand implements WeatherAPICommand{
	
	private WeatherAPIKey key;

	@Override
	public String execute(WeatherAPIKey key, WeatherAPIGeoLocation location) {
		
		ForecastRequest request = new ForecastRequestBuilder()
		        .key(new APIKey(key.getSecretKey()))
		        .exclude(ForecastRequestBuilder.Block.minutely)
		        .extendHourly()
		        .location(new GeoCoordinates(new Longitude(location.getLongit()), new Latitude(location.getLat()))).build();
		
        DarkSkyJacksonClient client = new DarkSkyJacksonClient();
        StringBuilder json = new StringBuilder(0);
        json.append("{");
        try {
            Forecast forecast = client.forecast(request);
            json.append("\"latitude\" : \"").append(forecast.getLatitude()).append("\",");
            json.append("\"longitude\" : \"").append(forecast.getLongitude()).append("\",");
            json.append("\"hourly\" : {");
            json.append("\"summary\" : \"").append(forecast.getCurrently().getSummary()).append("\",");
            json.append("\"icon\" : \"").append(forecast.getCurrently().getIcon()).append("\",");
            json.append("\"data\" : [");
            List<DataPoint> hourlyDataPoints = forecast.getHourly().getData();
            
            // We are only interested in next 24 hours. It return hourly for
            // entire week. Hence returning 24
            int hourlyDataPointsSize = (hourlyDataPoints != null)?24:0;
            for(int i=0; i<hourlyDataPointsSize; i++) {
                DataPoint dataPoint = hourlyDataPoints.get(i);
                json.append("{");
                json.append("\"time\" : \"").append(dataPoint.getTime()).append("\",");
                json.append("\"summary\" : \"").append(dataPoint.getSummary()).append("\",");
                json.append("\"icon\" : \"").append(dataPoint.getIcon()).append("\",");
                json.append("\"precipitation\" : \"").append(dataPoint.getPrecipIntensity()).append("\",");
                json.append("\"precipitation_probability\" : \"").append(dataPoint.getPrecipProbability()).append("\",");
                json.append("\"temperature\" : \"").append(dataPoint.getTemperature()).append("\",");
                json.append("\"real_feel_temperature\" : \"").append(dataPoint.getApparentTemperature()).append("\",");
                json.append("\"humidity\" : \"").append(dataPoint.getHumidity()).append("\",");
                json.append("\"wind_speed\" : \"").append(dataPoint.getWindSpeed()).append("\",");
                json.append("\"wind_bearing\" : \"").append(dataPoint.getWindBearing()).append("\",");
                json.append("\"pressure\" : \"").append(dataPoint.getPressure()).append("\",");
                json.append("\"visibility\" : \"").append(dataPoint.getVisibility()).append("\"");
                if(i == hourlyDataPointsSize-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            json.append("]},");
            json.append("\"daily\" : [");
            
            List<DailyDataPoint> dailyDataPoints = forecast.getDaily().getData();
            int dailyDataPointsSize = (dailyDataPoints != null)?dailyDataPoints.size():0;
            for(int i=0; i<dailyDataPointsSize; i++) {
                DailyDataPoint dailyDataPoint = dailyDataPoints.get(i);
                json.append("{");
                json.append("\"time\" : \"").append(dailyDataPoint.getTime()).append("\",");
                json.append("\"summary\" : \"").append(dailyDataPoint.getSummary()).append("\",");
                json.append("\"min_temperature\" : \"").append(dailyDataPoint.getTemperatureMin()).append("\",");
                json.append("\"max_temperature\" : \"").append(dailyDataPoint.getTemperatureMax()).append("\"");
                if(i == dailyDataPoints.size()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            json.append("],");
            json.append("\"alerts\" : [");
            List<Alert> alerts =  forecast.getAlerts();
            int alertsSize = (alerts != null)?alerts.size():0;
            for(int i=0; i<alertsSize; i++) {
                Alert alert = alerts.get(i);
                json.append("{");
                json.append("\"title\" : \"").append(alert.getTitle()).append("\",");
                json.append("\"time\" : \"").append(alert.getTime()).append("\",");
                json.append("\"expires\" : \"").append(alert.getExpires()).append("\",");
                if(i == alerts.size()-1) {
                    json.append("}");
                } else {
                    json.append("},");
                }
            }
            json.append("]");
            json.append("}");
        } catch (ForecastException ex) {
            ex.printStackTrace();
        }
        
        return json.toString();
	}

	@Override
	public String parseResponse(String JSONResponse) {
		
		JSONObject obj = new JSONObject(JSONResponse);
		JSONObject output = new JSONObject();
		
		// parse only the set of attributes we decided on
		

		return null;
	}

	public WeatherAPIKey getKey() {
		return key;
	}

	public void setKey(WeatherAPIKey key) {
		this.key = key;
	}
}
