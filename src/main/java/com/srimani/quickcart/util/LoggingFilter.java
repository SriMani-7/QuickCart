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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@code LoggingFilter} is a servlet filter that logs HTTP requests.
 * <p>
 * This filter logs the HTTP method and request URI of each incoming request. It
 * is useful for monitoring and debugging purposes as it provides insight into
 * the requests being processed by the web application.
 * </p>
 * <p>
 * The filter is applied to all URL patterns ("/") as specified in the
 * {@code urlPatterns} attribute.
 * </p>
 */
@WebFilter(filterName = "LoggingFilter", urlPatterns = { "/*" })
public class LoggingFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Logger logger = LogManager.getLogger();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		logger.info("%s %s".formatted(httpRequest.getMethod(), httpRequest.getRequestURI()));
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

}
