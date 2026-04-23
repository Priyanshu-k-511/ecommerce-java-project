package com.ecommerce.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        LOGGER.info("LogoutServlet: doGet() invoked.");

        // 1. Retrieve the current HttpSession (false means don't create a new one if it doesn't exist)
        HttpSession session = request.getSession(false);
        
        // 2. If the session exists, invalidate it to clear all attributes and log the user out
        if (session != null) {
            LOGGER.info("LogoutServlet: Found active session. Invalidating...");
            session.invalidate();
        } else {
            LOGGER.info("LogoutServlet: No active session found to invalidate.");
        }

        // 3. Set a success message attribute
        request.setAttribute("successMessage", "Successfully logged out.");
        
        // 4. Redirect (forward) the user back to login.jsp with the message
        // Using forward instead of sendRedirect so the request attribute "successMessage" is preserved 
        // and automatically displayed by the JSTL tags in login.jsp.
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
