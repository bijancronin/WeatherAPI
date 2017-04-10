package com.weatherAPI.servlet;

import com.weatherAPI.userProfileManager.UserSettings;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteLocationServlet extends HttpServlet {

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
            String username = (String)session.getAttribute("username");
            UserSettings userSettings = new UserSettings();
            String defaultLocation = request.getParameter("defaultLocation");
            String favoriteLocation = request.getParameter("favoriteLocation");
            if(defaultLocation != null) {
                userSettings.deleteDefaultLocation(username);
            } else if(favoriteLocation != null) {
                userSettings.deleteFavoriteLocation(request.getParameter("locationId"));
            }
            dispatcher = request.getRequestDispatcher("/user/GetDefaultAndFavoriteLocationsServlet");
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
