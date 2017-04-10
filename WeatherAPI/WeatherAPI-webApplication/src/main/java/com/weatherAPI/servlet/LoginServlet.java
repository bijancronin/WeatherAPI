package com.weatherAPI.servlet;

import com.weatherAPI.userProfileManager.UserProfile;
import com.weatherAPI.userProfileManager.UserProfileBean;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

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
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UserProfile userProfile = new UserProfile();
            UserProfileBean userProfileBean = userProfile.authenticateUser(username, password);
            if (userProfileBean != null) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("name", userProfileBean.getName());
                dispatcher = request.getRequestDispatcher("/user/UserHomepage.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("error", "Invalid username/password.");
                dispatcher = request.getRequestDispatcher("/index.jsp");
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
