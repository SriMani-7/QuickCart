<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Your Cart"/>
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
    <h1 class="mb-4">Shopping Cart</h1>

    <c:choose>
        <c:when test="${not empty cartItems}">
            <!-- Cart Items Table -->
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">Product</th>
                        <th scope="col">Description</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Total</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>${item.productName}</td>
                            <td>${item.description}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart" method="post" class="d-flex">
                                    <input type="hidden" name="productId" value="${item.productId}">
                                    <input type="hidden" name="action" value="update">
                                    <input type="number" name="quantity" class="form-control me-2" value="${item.quantity}" min="1">
                                    <button type="submit" class="btn btn-primary">Update</button>
                                </form>
                            </td>
                            <td>&#8377; ${item.price}</td>
                            <td>&#8377; ${item.price * item.quantity}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart" method="post">
                                    <input type="hidden" name="productId" value="${item.productId}">
                                    <input type="hidden" name="action" value="delete">
                                    <button type="submit" class="btn btn-danger">Remove</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Total Price -->
            <div class="text-end">
                <h4>Total Price: &#8377; ${totalPrice}</h4>
                <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success mt-3">Proceed to Checkout</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning text-center" role="alert">
                Your cart is empty.
            </div>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Continue Shopping</a>
        </c:otherwise>
    </c:choose>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
