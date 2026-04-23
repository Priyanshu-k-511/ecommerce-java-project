package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        LOGGER.info("LoginServlet: doPost() invoked.");

        // 1. Retrieve the parameters from login.jsp
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Basic server-side validation
        if (email == null || password == null || email.trim().isEmpty() || password.isEmpty()) {
            LOGGER.warning("LoginServlet: Missing email or password.");
            request.setAttribute("errorMessage", "Email and password are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            LOGGER.info("LoginServlet: Attempting login verification for email: " + email);
            
            // Instantiate DAO
            UserDAO userDAO = new UserDAO();
            
            // 2. Call UserDAO.verifyLogin(email, password)
            boolean isValid = userDAO.verifyLogin(email, password);
            
            if (isValid) {
                LOGGER.info("LoginServlet: Credentials verified successfully. Fetching user details.");
                
                // 3. IF TRUE: Fetch the User object
                User user = userDAO.getUserByEmail(email);
                
                if (user != null) {
                    // Create an HttpSession and set the User object as a session attribute
                    HttpSession session = request.getSession();
                    session.setAttribute("currentUser", user);
                    
                    LOGGER.info("LoginServlet: Session created for user. Redirecting to products.jsp.");
                    
                    // Redirect to products.jsp using PRG (Post/Redirect/Get) pattern
                    response.sendRedirect("products.jsp");
                } else {
                    LOGGER.warning("LoginServlet: Login verified but User details could not be retrieved.");
                    request.setAttribute("errorMessage", "An error occurred retrieving your profile.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                
            } else {
                // 4. IF FALSE: Redirect back to login.jsp with an "Invalid credentials" error message
                LOGGER.warning("LoginServlet: Invalid credentials provided for email: " + email);
                request.setAttribute("errorMessage", "Invalid credentials. Please try again.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            // 5. Use rigorous try-catch blocks and standard logging
            LOGGER.log(Level.SEVERE, "LoginServlet: Exception occurred during the login process: " + e.getMessage(), e);
            
            request.setAttribute("errorMessage", "Login failed due to an internal server error. Please try again later.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
