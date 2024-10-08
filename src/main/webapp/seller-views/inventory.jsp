<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Inventory Management" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Inventory Management</h1>

    <!-- Add New Product Button -->
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/retailer/inventory/add" class="btn btn-success">Add New Product</a>
    </div>

    <!-- Inventory Table -->
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <th scope="col">Product ID</th>
                <th scope="col">Image</th> <!-- New Image Column -->
                <th scope="col">Product Name</th>
                <th scope="col">Description</th>
                <th scope="col">Category</th>
                <th scope="col">Price</th>
               
                <th scope="col">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${inventory}">
                <tr>
                    <td>${product.id}</td>
                    <td>
                        <!-- Display Product Image -->
                        <img src="${product.imageUrl}" alt="${product.name}" class="img-fluid" style="max-width: 100px; max-height: 100px;">
                    </td>
                    <td><a href="${pageContext.request.contextPath}/retailer/inventory?productId=${product.id}">${product.name}</a></td>
                    <td>${product.description}</td>
                    <td>${product.category}</td>
                    <td>&#8377;${product.price}</td>
                    
                    <td>
                        <a href="${pageContext.request.contextPath}/retailer/inventory/edit?id=${product.id}" class="btn btn-warning btn-sm">Edit</a>
                        <form method="post" action="${pageContext.request.contextPath}/retailer/inventory" style="display:inline;">
                            <input type="hidden" name="_method" value="DELETE">
                            <input type="hidden" name="id" value="${product.id}">
                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
