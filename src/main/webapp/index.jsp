<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
  
    <style>
    /* Style for the hero section with background image */
.hero {
    background-image: url('images/background.png');
    background-size: cover; /* Ensure the background image covers the entire section */
    background-position: center; /* Center the background image */
    background-repeat: no-repeat; /* Prevent the background image from repeating */
}

/* Add any additional styling for .glassy-btn if needed */
.glassy-btn {
   
    border: 1px solid rgba(255, 255, 255, 0.8); /* White border with some opacity */
    color: #fff; /* White text color */
    border-radius: 4px; /* Rounded corners */
    padding: 10px 20px; /* Padding around the text */
    transition: background 0.3s, border-color 0.3s; /* Smooth transition effects */
}

.glassy-btn:hover {
    background: rgba(255, 255, 255, 0.8); /* More opaque on hover */
    border-color: rgba(255, 255, 255, 1); /* Solid white border on hover */
}
    
    </style>
</head>
<body>
    <!-- Main Section -->
    <main>
        <!-- Hero Section with Gradient Background -->
        <div class="hero bg-image d-flex flex-column justify-content-center align-items-center text-center py-5">
            <h1 class="display-3 text-white mb-4">Welcome to Our QuickCart Store</h1>
            <p class="lead text-white mb-4">Browse our wide range of products and enjoy great deals!</p>
            <a href="${pageContext.request.contextPath}/products" class="btn glassy-btn">Browse Products</a>
            <a href="${pageContext.request.contextPath}/login" class="btn glassy-btn mt-3">Login</a>
        </div>

        <!-- Benefits Cards -->
        <div class="container my-5">
            <div class="row text-center">
                <!-- Buyer Benefits Card -->
                <div class="col-md-6 mb-4">
                    <div class="card shadow-sm border-light">
                        <div class="card-body">
                            <h5 class="card-title">Benefits for Buyers</h5>
                            <p class="card-text">Enjoy exclusive discounts, a wide range of products, and a seamless shopping experience.</p>
                            <a href="${pageContext.request.contextPath}/signup" class="btn btn-primary">Create Account</a>
                        </div>
                    </div>
                </div>
                <!-- Retailer Benefits Card -->
                <div class="col-md-6 mb-4">
                    <div class="card shadow-sm border-light">
                        <div class="card-body">
                            <h5 class="card-title">Benefits for Retailers</h5>
                            <p class="card-text">Access a vast market, manage your inventory efficiently, and boost your sales with us.</p>
                            <a href="${pageContext.request.contextPath}/signup" class="btn btn-primary">Create Account</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Footer -->
    <footer class="bg-light text-center py-4">
        <p class="mb-0">© 2024 QuickCart Website. All rights reserved.</p>
        <p class="mb-0">
            <a href="${pageContext.request.contextPath}/contact" class="text-dark">Contact Us</a> |
            <a href="${pageContext.request.contextPath}/about" class="text-dark">About Us</a>
        </p>
    </footer>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
