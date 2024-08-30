<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Your Orders" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Your Orders</h1>

    <c:if test="${not empty orders}">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Total Price</th>
                    <th>Order Date</th>
                    <th>Payment Method</th>
                    <th>Shipping Address</th>
                    <th>City</th>
                    <th>Pincode</th>
                    <th>Phone Number</th>
                    <th>Status</th>
                    <th>Details</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td><c:out value="${order.orderId}" /></td>
                        <td>$<c:out value="${order.totalPrice}" /></td>
                        <td><c:out value="${order.orderDate}" /></td>
                        <td><c:out value="${order.paymentMethod}" /></td>
                        <td><c:out value="${order.shippingAddress}" /></td>
                        <td><c:out value="${order.city}" /></td>
                        <td><c:out value="${order.pincode}" /></td>
                        <td><c:out value="${order.phoneNumber}" /></td>
                        <td><c:out value="${order.status}" /></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/orders/info?id=${order.orderId}" class="btn btn-info btn-sm">View Details</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty orders}">
        <div class="alert alert-info" role="alert">
            You have no orders yet.
        </div>
    </c:if>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
