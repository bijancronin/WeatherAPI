package com.weather.apiManager.dl;

import java.sql.Timestamp;

public class APIResponseBean {
    private String requestId;
    private double latitude;
    private double longitude;
    private String city;
    private String state;
    private String country;
    private String api;
    private String json;
    private Timestamp requestTime;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getState() {
        return state;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getCountry() {
        return country;
    }
}
