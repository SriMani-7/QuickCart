<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="My Reviews" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">My Reviews</h1>

    <c:choose>
        <c:when test="${not empty userReviews}">
            <ul class="list-group">
                <c:forEach var="review" items="${userReviews}">
                    <li class="list-group-item">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="mb-1">${review.productName}</h5>
                                <p class="mb-1">${review.message}</p>
                                <small>Rating: ${review.rating} / 5</small>
                            </div>
                            <form method="post" action="${pageContext.request.contextPath}/reviews">
                                <input type="hidden" name="productId" value="${review.productId}">
                                <input type="hidden" name="action" value="delete">
                                <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                            </form>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info" role="alert">You have not submitted any reviews yet.</div>
        </c:otherwise>
    </c:choose>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
