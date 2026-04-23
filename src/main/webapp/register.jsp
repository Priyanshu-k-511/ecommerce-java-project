<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .container { max-width: 500px; margin-top: 50px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="card shadow-sm">
            <div class="card-body">
                <h3 class="card-title text-center mb-4">Register</h3>
                
                <%-- Display Error Message from Servlet --%>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                        ${errorMessage}
                    </div>
                </c:if>
                
                <%-- Display Success Message from Servlet --%>
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success" role="alert">
                        ${successMessage}
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/RegisterServlet" method="post" id="registerForm" onsubmit="return validateForm()">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email address</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                    </div>
                    
                    <div id="validationMessage" class="text-danger mb-3" style="display: none;"></div>
                    
                    <button type="submit" class="btn btn-primary w-100">Register</button>
                </form>
                
                <div class="mt-3 text-center">
                    <p>Already have an account? <a href="login.jsp" class="text-decoration-none">Login here</a></p>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Vanilla JS Validation -->
    <script>
        function validateForm() {
            const name = document.getElementById('name').value.trim();
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const validationMessage = document.getElementById('validationMessage');
            
            // Reset message
            validationMessage.style.display = 'none';
            validationMessage.innerHTML = '';

            // 1. Check for empty fields
            if (name === '' || email === '' || password === '' || confirmPassword === '') {
                showError('All fields are required.');
                return false;
            }

            // 2. Validate Email format
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                showError('Please enter a valid email address.');
                return false;
            }

            // 3. Password length validation
            if (password.length < 6) {
                showError('Password must be at least 6 characters long.');
                return false;
            }

            // 4. Check if passwords match
            if (password !== confirmPassword) {
                showError('Passwords do not match.');
                return false;
            }

            return true; // Form is valid, allow submission
        }

        function showError(message) {
            const validationMessage = document.getElementById('validationMessage');
            validationMessage.innerHTML = message;
            validationMessage.style.display = 'block';
        }
    </script>
</body>
</html>
