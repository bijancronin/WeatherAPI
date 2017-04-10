/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weatherAPI.servlet;

import com.weatherAPI.userProfileManager.UserProfile;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateAccountServlet extends HttpServlet {

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
            String password = request.getParameter("password");
            String newPassword = request.getParameter("new_password");
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");
            UserProfile userProfile = new UserProfile();
            
            if(userProfile.authenticateUser(
                    (String)session.getAttribute("username"), password) == null) {
                request.setAttribute("error", "Invalid Password");
                dispatcher = request.getRequestDispatcher("/user/UserAccount.jsp");
                dispatcher.forward(request, response);
                return;
            }
            
            if(newPassword != null && !newPassword.trim().equals("")) {
                password = newPassword;
            }
            
            boolean isUpdated = userProfile.updateUser(name, username, password);
            if(isUpdated) {
                request.setAttribute("message", "Profile Updated");
                session.setAttribute("username", username);
                session.setAttribute("name", name);
                dispatcher = request.getRequestDispatcher("/user/UserAccount.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("error", "Something went wrong.");
                dispatcher = request.getRequestDispatcher("/user/UserAccount.jsp");
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
