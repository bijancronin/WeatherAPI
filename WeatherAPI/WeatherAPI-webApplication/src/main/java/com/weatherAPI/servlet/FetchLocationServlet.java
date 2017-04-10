package com.weatherAPI.servlet;

import com.google.gson.Gson;
import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.weather.apiManager.util.GeoLocationManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FetchLocationServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String city = request.getParameter("city");
        if (city == null || city.isEmpty()) {
            System.out.println("Empty City");
            return;
        }
        GeoLocationManager locationManager = new GeoLocationManager();
        ArrayList<WeatherAPIGeoLocation> locations
                = locationManager.getLocationByCityName(city);
        if (locations.isEmpty()) {
            System.out.println("No locations Found");
            return;
        }
        Gson gson = new Gson();
        String jsonLocations = gson.toJson(locations);
        out.print(jsonLocations);
        out.close();
        System.out.println(jsonLocations);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
