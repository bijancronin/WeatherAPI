package com.weatherAPI.servlet;

import com.weatherAPI.userProfileManager.UserSettings;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUserAPISubscriptionServlet extends HttpServlet {

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
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            UserSettings userSettings = new UserSettings();
            ArrayList<String> apiSubscription = userSettings.getUserAPISubscriptions(username);
            request.setAttribute("apixu", (apiSubscription.contains("apixu")) ? "checked" : "");
            request.setAttribute("foreca", (apiSubscription.contains("foreca")) ? "checked" : "");
            request.setAttribute("openweathermap", (apiSubscription.contains("openweathermap")) ? "checked" : "");
            request.setAttribute("wunder", (apiSubscription.contains("wunder")) ? "checked" : "");
            request.setAttribute("yahoo", (apiSubscription.contains("yahoo")) ? "checked" : "");
            request.setAttribute("darksky", (apiSubscription.contains("darksky")) ? "checked" : "");
            dispatcher = request.getRequestDispatcher("/user/UserAPISetting.jsp");
            dispatcher.forward(request, response);
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
