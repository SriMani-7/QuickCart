package com.srimani.quickcart.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = { "/retailer/*", "/admin/*", "/orders", "/cart",
		"/reviews" })
public class RoleAccessFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		if (session == null || session.getAttribute("user-role") == null) {
			// No session or no user-role attribute found, redirect to login page
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Please log in.");
			return;
		}

		String userRole = (String) session.getAttribute("user-role");
		String requestURI = httpRequest.getRequestURI();

		// Check for access to /retailer/* URLs
		if (requestURI.startsWith(httpRequest.getContextPath() + "/retailer") && !userRole.equals("SELLER")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Insufficient permissions.");
			return;
		}

		// Check for access to /admin/* URLs
		if (requestURI.startsWith(httpRequest.getContextPath() + "/admin") && !userRole.equals("ADMIN")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Insufficient permissions.");
			return;
		}

		// If the user has the correct role, continue with the request
		chain.doFilter(request, response);

	}

}
