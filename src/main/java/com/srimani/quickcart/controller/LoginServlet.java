package com.srimani.quickcart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.srimani.quickcart.exception.UserNotExistsException;
import com.srimani.quickcart.service.AuthenticationService;
import com.srimani.quickcart.util.ServiceFactory;

/**
 * {@code LoginServlet} handles user authentication and login requests.
 * <p>
 * This servlet processes login requests from users by validating their
 * credentials. Upon successful authentication, it establishes an HTTP session
 * and redirects users based on their roles (e.g., buyer, seller, admin). If
 * authentication fails, it forwards the request back to the login page with an
 * error message.
 * </p>
 * 
 * @see AuthenticationService
 * @see UserNotExistsException
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AuthenticationService authenticationService;

	@Override
	public void init() throws ServletException {
		// Initialize AuthService with the appropriate DAO implementation
		authenticationService = ServiceFactory.getAuthenticationService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward the request to the login JSP page
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get username and password from the request
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			var user = authenticationService.authenticate(username, password);
			HttpSession session = request.getSession();
			session.setAttribute("user-id", user.getId());
			session.setAttribute("user-role", user.getRole());
			session.setAttribute("username", user.getUsername());
			session.setAttribute("email", user.getEmail());
			// Role-based redirection
			switch (user.getRole()) {
			case "BUYER":
				response.sendRedirect(request.getContextPath() + "/products");
				break;
			case "SELLER":
				response.sendRedirect(request.getContextPath() + "/retailer/inventory");
				break;
			case "ADMIN":
				response.sendRedirect(request.getContextPath() + "/admin/users");
				break;
			default:
				// Handle any unknown roles (optional)
				response.sendRedirect(request.getContextPath() + "/");
				break;
			}
		} catch (UserNotExistsException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Invalid username or password.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
