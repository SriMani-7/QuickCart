<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Checkout" />
</jsp:include>

<!-- Main Content -->
<div class="container mt-5">
	<h1 class="mb-4">Checkout</h1>

	<form method="post"
		action="${pageContext.request.contextPath}/checkout">
		<div class="row">
			<!-- Shipping Details -->
			<div class="col-md-6">
				<h3>Shipping Details</h3>
				<div class="mb-3">
					<label for="city" class="form-label">City</label> <input
						type="text" class="form-control" id="city" name="city" required>
				</div>
				<div class="mb-3">
					<label for="address" class="form-label">Shipping address</label> <input
						type="text" class="form-control" id="address" name="address"
						required>
				</div>
				<div class="mb-3">
					<label for="pincode" class="form-label">Pincode</label> <input
						type="text" class="form-control" id="pincode" name="pincode"
						required>
				</div>
				<div class="mb-3">
					<label for="phoneNumber" class="form-label">Phone Number</label> <input
						type="text" class="form-control" id="phoneNumber"
						name="phoneNumber" required>
				</div>
			</div>

			<!-- Payment Details -->
			<div class="col-md-6">
				<h3>Payment Details</h3>
				<div class="mb-3">
					<label for="paymentMethod" class="form-label">Payment
						Method</label> <select class="form-select" id="paymentMethod"
						name="paymentMethod" required>
						<option value="cod">Cash on Delivery</option>
					</select>
				</div>
			</div>
		</div>

		<!-- Total Price -->
		<div class="text-end mt-4">
			<h4>Total Payable Amount: &#8377;${totalPrice}</h4>
			<button type="submit" class="btn btn-success">Place Order</button>
		</div>
	</form>
</div>

<!-- Bootstrap JS and dependencies -->
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
