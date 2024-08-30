<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container mt-5">
        <h1 class="mb-4 text-center">Manage Users</h1>
        
        <!-- Users Table -->
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>S.No</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>User Type</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.userType}</td>
                        <td>${user.createdAt}</td>
                        <td>
                            <!-- Conditional Button Display -->
                            <c:choose>
                                <c:when test="${user.status eq 'ACTIVE'}">
                                    <!-- Block User Form -->
                                    <form method="post" action="${pageContext.request.contextPath}/admin/users" style="display:inline;">
                                        <input type="hidden" name="userId" value="${user.id}">
                                        <input type="hidden" name="action" value="block">
                                        <button type="submit" class="btn btn-warning btn-sm">Block</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <!-- Activate User Form -->
                                    <form method="post" action="${pageContext.request.contextPath}/admin/users" style="display:inline;">
                                        <input type="hidden" name="userId" value="${user.id}">
                                        <input type="hidden" name="action" value="activate">
                                        <button type="submit" class="btn btn-success btn-sm">Activate</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>

                            <!-- Delete User Form (always available) -->
                            <form method="post" action="${pageContext.request.contextPath}/admin/users" style="display:inline;">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="hidden" name="action" value="delete">
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
