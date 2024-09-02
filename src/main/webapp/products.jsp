<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Navbar -->
<html>

<head>
<style>
.prodcut-desc {
height: 5em;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>
</head>

<body>

<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Product Details" />
</jsp:include>
<!-- Main Content -->
<div class="container mt-5">
	<h1 class="mb-4">Product List</h1>
	
	<!-- Category Dropdown -->
	<div class="mb-4">
		<form action="${pageContext.request.contextPath}/products" method="get">
			<div class="input-group">
				<select class="form-select" name="category" onchange="this.form.submit()">
					<option value="">Select Category</option>
					<c:forEach var="category" items="${categories}">
						<option value="${category}" <c:if test="${category == param.category}">selected</c:if>>${category}</option>
					</c:forEach>
				</select>
			</div>
		</form>
	</div>

	<div class="row row-cols-1 row-cols-md-4 g-4">
		<c:forEach var="product" items="${products}">
			<div class="col">
				<div class="card">
					<img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
					<div class="card-body">
						<h5 class="card-title">${product.name}</h5>
						<p class="card-text   text-truncate">${product.description}</p>
						<p class="card-text">
							<strong>Price:</strong> &#8377;${product.price}
						</p>
						<a
							href="${pageContext.request.contextPath}/products/info?id=${product.id}"
							class="btn btn-info">View Details</a>
					</div>
				</div>
			</div>
		</c:forEach>
		<c:if test="${empty products}">
			<div class="col">
				<div class="alert alert-warning text-center" role="alert">No
					products found.</div>
			</div>
		</c:if>
	</div>
</div>

<!-- Bootstrap JS and dependencies -->
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
