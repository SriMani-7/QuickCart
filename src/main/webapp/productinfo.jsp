<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Product Details"/>
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Product Details</h1>

    <c:choose>
        <c:when test="${not empty product}">
            <div class="card mb-4">
                <%-- Uncomment the following line if you have an image URL for the product --%>
                
                <div class="card-body">
                    <h2 class="card-title">${product.name}</h2>
                    <p class="card-text">${product.description}</p>
                    <p class="card-text"><strong>Category:</strong> ${product.category}</p>
                    <p class="card-text"><strong>Price:</strong> $${product.price}</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Back to Products</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-danger" role="alert">
                Product not found.
            </div>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Back to Products</a>
        </c:otherwise>
    </c:choose>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
