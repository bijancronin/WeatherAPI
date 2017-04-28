package com.weatherAPI.servlet;

import com.weather.apiManager.dl.APIResponsesDAO;
import com.weatherAPI.userProfileManager.UserSettings;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserAPISettingsServlet extends HttpServlet {
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
            ArrayList<String> apiSubscription = new ArrayList<>(0);
            if(request.getParameter("accuweather") != null) 
                apiSubscription.add(APIResponsesDAO.ACCUWEATHER);
            if(request.getParameter("apixu") != null) 
                apiSubscription.add(APIResponsesDAO.APIXU);
            if(request.getParameter("darksky") != null) 
                apiSubscription.add(APIResponsesDAO.DARK_SKY);
            if(request.getParameter("foreca") != null) 
                apiSubscription.add(APIResponsesDAO.FORECA);
            if(request.getParameter("openweathermap") != null) 
                apiSubscription.add(APIResponsesDAO.OPEN_WEATHER_MAP);
            if(request.getParameter("wunder") != null) 
                apiSubscription.add(APIResponsesDAO.WUNDER);
            if(request.getParameter("weatherbit") != null) 
                apiSubscription.add(APIResponsesDAO.WEATHERBIT);
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");
            UserSettings userSettings = new UserSettings();
            userSettings.deleteSubscriptions(username);
            for(String api : apiSubscription) {
                userSettings.addSubscription(username, api);
            }
            request.setAttribute("accuweather", (request.getParameter("accuweather") == null)?"":"checked");
            request.setAttribute("apixu", (request.getParameter("apixu") == null)?"":"checked");
            request.setAttribute("darksky", (request.getParameter("darksky") == null)?"":"checked");
            request.setAttribute("openweathermap", (request.getParameter("openweathermap") == null)?"":"checked");
            request.setAttribute("wunder", (request.getParameter("wunder") == null)?"":"checked");
            request.setAttribute("weatherbit", (request.getParameter("weatherbit") == null)?"":"checked");
            request.setAttribute("foreca", (request.getParameter("foreca") == null)?"":"checked");
            dispatcher = request.getRequestDispatcher("/user/UserAPISetting.jsp");
            dispatcher.forward(request, response);
        } catch(Exception e) {
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
        doGet(request,response);
    }
}
