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
 * Servlet Filter implementation class LoggingFilter
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
