package com.weatherAPI.servlet;

import com.weather.apiManager.command.WeatherAPIGeoLocation;
import com.weatherAPI.userProfileManager.UserSettings;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddFavoriteLocationServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = null;
        try {
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            String state = request.getParameter("state");
            String city = request.getParameter("city");
            String zipcode = request.getParameter("zipcode");
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");

            if (latitude == null || longitude == null || state == null
                    || city == null || zipcode == null) {
                request.setAttribute("error", "Bad request");
                dispatcher = request.getRequestDispatcher("/user/UserLocationSetting.jsp");
                dispatcher.forward(request, response);
                return;
            }

            WeatherAPIGeoLocation location = new WeatherAPIGeoLocation();
            location.setLat(Double.valueOf(latitude));
            location.setLongit(Double.valueOf(longitude));
            location.setState(state);
            location.setCity(city);
            location.setZipcode(zipcode);
            UserSettings userSettings = new UserSettings();
            boolean isAdded = userSettings.addFavoriteLocation(username, location);
            if (isAdded) {
                request.setAttribute("message", "Location added to favorites");
                dispatcher = request.getRequestDispatcher
                        ("/user/GetDefaultAndFavoriteLocationsServlet");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("error"
                        , "Something went wrong. Unable to add favorite location.");
                dispatcher = request.getRequestDispatcher
                        ("/user/GetDefaultAndFavoriteLocationsServlet");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
