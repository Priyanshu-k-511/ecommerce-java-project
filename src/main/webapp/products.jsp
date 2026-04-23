<%@ page import="com.ecommerce.model.Product" %>
<%@ page import="com.ecommerce.dao.ProductDAO" %>
<%@ page import="java.util.List" %>
<%@ include file="header.jsp" %>
<%
    ProductDAO productDAO = new ProductDAO();
    List<Product> products = productDAO.getAllProducts();
%>
<div class="row mt-5">
    <div class="col-12">
        <h2 class="mb-4">Our Products</h2>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <% if (products != null && !products.isEmpty()) {
                for (Product p : products) { %>
            <div class="col">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><%= p.getName() %></h5>
                        <p class="card-text"><%= p.getDescription() %></p>
                        <p class="card-text"><strong>$<%= String.format("%.2f", p.getPrice()) %></strong></p>
                    </div>
                    <div class="card-footer bg-white border-top-0">
                        <form action="cart.jsp" method="post">
                            <input type="hidden" name="productId" value="<%= p.getId() %>">
                            <button type="submit" class="btn btn-primary w-100">Add to Cart</button>
                        </form>
                    </div>
                </div>
            </div>
            <%      }
               } else { %>
            <div class="col-12">
                <p>No products found.</p>
            </div>
            <% } %>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
