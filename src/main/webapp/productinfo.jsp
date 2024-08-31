<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Product Details" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Product Details</h1>

    <div class="row">
        <!-- Product Image Section (Left Side) -->
        <div class="col-md-4">
            <c:choose>
                <c:when test="${not empty product.imageUrl}">
                    <img src="${product.imageUrl}" alt="${product.name}" class="img-fluid mb-4">
                </c:when>
                <c:otherwise>
                    <img src="https://via.placeholder.com/400" alt="Placeholder Image" class="img-fluid mb-4">
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Product Details and Reviews Section (Right Side) -->
        <div class="col-md-8">
            <c:choose>
                <c:when test="${not empty product}">
                    <div class="card mb-4">
                        <div class="card-body">
                            <h2 class="card-title">${product.name}</h2>
                            <p class="card-text">${product.description}</p>
                            <p class="card-text">
                                <strong>Category:</strong> ${product.category}
                            </p>
                            <p class="card-text">
                                <strong>Price:</strong> $${product.price}
                            </p>

                            <c:choose>
                                <c:when test="${incart}">
                                    <a href="${pageContext.request.contextPath}/cart" class="btn btn-primary">View in Cart</a>
                                </c:when>
                                <c:otherwise>
                                    <!-- Add to Cart Button -->
                                    <form method="post" action="${pageContext.request.contextPath}/cart">
                                        <input type="hidden" name="product-id" value="${product.id}">
                                        <button type="submit" class="btn btn-success mt-3">Add to Cart</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>

                            <!-- Seller Information -->
                            <c:if test="${not empty retailer}">
                            <div class="mt-4">
                                <h4>Seller Information</h4>
                                <p><strong>Name:</strong> ${retailer.name}</p>
                                <p><strong>Address:</strong> ${retailer.address}</p>
                            </div>
                            </c:if>
                            

                            <!-- Reviews Section -->
                            <h3 class="mb-4 mt-4">Reviews</h3>
                            <c:choose>
                                <c:when test="${not empty reviews}">
                                    <ul class="list-group mb-4">
                                        <c:forEach var="review" items="${reviews}">
                                            <li class="list-group-item">
                                                <h5 class="mb-1">${review.username}</h5>
                                                <p class="mb-1">${review.message}</p> 
                                                <small>Rating: ${review.rating} / 5</small>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-info" role="alert">No reviews yet.</div>
                                </c:otherwise>
                            </c:choose>

                            <!-- Review Form -->
                            <h5 class="mb-4">Post a Review</h5>
                            <c:choose>
                                <c:when test="${sessionScope['user-role'] == 'BUYER'}">
                                    <form method="post" action="${pageContext.request.contextPath}/reviews">
                                        <input type="hidden" name="productId" value="${product.id}">
                                        <div class="mb-3">
                                            <label for="reviewText" class="form-label">Your Review</label>
                                            <textarea class="form-control" id="reviewText" name="reviewText" rows="4" required></textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="rating" class="form-label">Rating</label>
                                            <select class="form-select" id="rating" name="rating" required>
                                                <option value="" disabled selected>Select a rating</option>
                                                <option value="1">1 - Poor</option>
                                                <option value="2">2 - Fair</option>
                                                <option value="3">3 - Good</option>
                                                <option value="4">4 - Very Good</option>
                                                <option value="5">5 - Excellent</option>
                                            </select>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Submit Review</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-info" role="alert">Please <a href="${pageContext.request.contextPath}/login">log in</a> to post a review.</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger" role="alert">Product not found.</div>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Back to Products</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
