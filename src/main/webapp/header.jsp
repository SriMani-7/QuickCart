<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${pageTitle}" /></title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/products">QuickCart</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/products">All Products</a>
                    </li>
                    <!-- Show additional options if the user is a buyer -->
                    <c:if test="${sessionScope['user-role'] == 'BUYER'}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/orders">My Orders</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/reviews">My Reviews</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/cart">My Cart</a>
                        </li>
                    </c:if>
                </ul>
                <!-- Search box -->
                <form class="d-flex" method="get" action="${pageContext.request.contextPath}/products">
                    <input class="form-control me-2" type="search" name="search" placeholder="Search products" aria-label="Search" value="${param.search}">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
                <!-- User greeting and login/logout button -->
                <ul class="navbar-nav ms-auto">
                    <c:choose>
                        <c:when test="${sessionScope['user-role'] == 'BUYER'}">
                            <li class="nav-item">
                                <a class="nav-link" href="#">Hi, ${sessionScope.username}</a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-outline-danger ms-3" href="${pageContext.request.contextPath}/logout">Logout</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</body>
</html>
