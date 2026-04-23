package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        LOGGER.info("RegisterServlet: doPost() invoked.");

        // 1. Retrieve the parameters from register.jsp
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Basic server-side validation check
        if (name == null || email == null || password == null || 
            name.trim().isEmpty() || email.trim().isEmpty() || password.isEmpty()) {
            LOGGER.warning("RegisterServlet: Missing or empty form parameters.");
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            LOGGER.info("RegisterServlet: Processing registration for email: " + email);
            
            // 2. Create a new User object with these parameters
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password); // Note: Always hash passwords in a real production environment
            
            // 3. Call the existing UserDAO.saveUser() method
            UserDAO userDAO = new UserDAO();
            userDAO.saveUser(user);
            
            LOGGER.info("RegisterServlet: User registered successfully in the database. Forwarding to login.jsp.");
            
            // 4. If successful, redirect to login.jsp with a success message
            request.setAttribute("successMessage", "Registration successful! Please log in.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        } catch (Exception e) {
            // 5. Use rigorous try-catch blocks and log exceptions
            LOGGER.log(Level.SEVERE, "RegisterServlet: Exception occurred during user registration: " + e.getMessage(), e);
            
            // If an exception occurs, redirect back to register.jsp with an error message
            request.setAttribute("errorMessage", "Registration failed due to an internal error. Please try again later.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
