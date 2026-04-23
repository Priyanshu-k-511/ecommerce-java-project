package com.ecommerce.servlet;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Cart> cartItems = (List<Cart>) session.getAttribute("cart");
        if (cartItems != null && !cartItems.isEmpty()) {
            double totalAmount = 0.0;
            for (Cart item : cartItems) {
                totalAmount += (item.getProduct().getPrice() * item.getQuantity());
            }

            try {
                // RMI Lookup for Dev 2's PaymentService
                java.rmi.Remote paymentService = java.rmi.Naming.lookup("rmi://localhost:1099/PaymentService");
                System.out.println("RMI PaymentService connected: " + paymentService);
            } catch (Exception e) {
                System.out.println("RMI connection failed!");
                e.printStackTrace();
            }

            Order newOrder = new Order(currentUser, new Date(), totalAmount);
            orderDAO.saveOrder(newOrder);

            session.removeAttribute("cart");
            response.sendRedirect("order-success.jsp");
        } else {
            response.sendRedirect("cart.jsp");
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
