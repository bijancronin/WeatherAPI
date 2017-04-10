package com.weatherAPI.servlet;

import com.weatherAPI.userProfileManager.UserProfile;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A Servlet class for fetching the user entered data in HTML and
 * storing it in the database after validating the entered field
 * values.
 * @author Aditya Solge
 */
public class CreateAccountServlet extends HttpServlet {

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
            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if(name == null || username == null || password == null
                    || name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                request.setAttribute("error", "All fields are mandatory.");
                dispatcher = request.getRequestDispatcher("/CreateAccount.jsp");
                dispatcher.forward(request, response);
                return;
            }
            UserProfile userProfile = new UserProfile();
            boolean taken = userProfile.checkUsernameExists(username);
            if(!taken) {
                boolean isCreated = userProfile.createUser(username, name, password);
                if(isCreated) {
                    request.setAttribute("message", "Account Created Successfully.");
                    dispatcher = request.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("error", "Something went wrong. Please try again.");
                    dispatcher = request.getRequestDispatcher("/CreateAccount.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                request.setAttribute("error", "Username Already Taken");
                dispatcher = request.getRequestDispatcher("/CreateAccount.jsp");
                dispatcher.forward(request, response);
            }
            
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
