package com.weatherAPI.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A Filter class for checking if user has access to requested
 * page.
 * @author Aditya Solge
 */
public class AuthenticationFilter implements Filter {
    
    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {        
        // no-op
    }
    
    /**
     * This method filters URI having user in it. For example,
     * /WeatherAPI/user/..., which is at the 2nd index if splited
     * by "/".
     * It redirects to index page if user is not logged in.
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        // Is of the form /WeatherAPI/xxx/xxx
        String requestURI = servletRequest.getRequestURI();
        
        // We are interested in value at 2nd index
        String requestURIParts[] = requestURI.split("/");
        HttpSession session = servletRequest.getSession();
        if(requestURIParts.length > 2 && requestURIParts[2].equals("user")) {
            if(session.getAttribute("username") == null) {
                servletResponse.sendRedirect(servletRequest.getContextPath() 
                        + "/index.jsp");
                return;
            }
        } else if(requestURIParts.length > 2 && requestURIParts[2].equals("libs")) {
            chain.doFilter(request, response);
        } else if(requestURIParts.length > 2 && requestURIParts[2].equals("rest")) {
            if(session.getAttribute("username") == null) {
                servletResponse.sendRedirect(servletRequest.getContextPath() 
                        + "/index.jsp");
                return;
            }
        } else {
            if(session.getAttribute("username") != null) {
                servletResponse.sendRedirect(servletRequest.getContextPath() 
                        + "/user/UserHomepage.jsp");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }
}
