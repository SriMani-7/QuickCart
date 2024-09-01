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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(filterName = "RoleAccessFilter", urlPatterns = { "/retailer/*", "/admin/*", "/orders", "/cart", "/reviews" })
public class RoleAccessFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Logger logger = LogManager.getLogger();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		String requestURI = httpRequest.getRequestURI();
		String method = httpRequest.getMethod();

		if (session == null || session.getAttribute("user-role") == null) {
			// No session or no user-role attribute found, redirect to login page
			logger.info(method + " " + requestURI + " unautherized access");
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Please log in.");
			return;
		}

		String userRole = (String) session.getAttribute("user-role");

		String[] buyerPaths = { "%s/orders", "%s/cart", "%s/reviews" };
		for (String uri : buyerPaths) {
			if (requestURI.startsWith(uri.formatted(httpRequest.getContextPath()))) {
				if (!userRole.equals("BUYER")) {
					logger.info(method + " " + requestURI + " has required BUYER role so, Acccess denied");
					httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
							"Access denied. Insufficient permissions.");
					return;
				}
			}
		}

		// Check for access to /retailer/* URLs
		if (requestURI.startsWith(httpRequest.getContextPath() + "/retailer") && !userRole.equals("SELLER")) {
			logger.info(method + " " + requestURI + " has required SELLER role so, Acccess denied");
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Insufficient permissions.");
			return;
		}

		// Check for access to /admin/* URLs
		if (requestURI.startsWith(httpRequest.getContextPath() + "/admin") && !userRole.equals("ADMIN")) {
			logger.info(method + " " + requestURI + " has required ADMIN role so, Acccess denied");
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Insufficient permissions.");
			return;
		}

		// If the user has the correct role, continue with the request
		logger.info(method + " " + requestURI + " access allowed for " + userRole);
		chain.doFilter(request, response);

	}

}
