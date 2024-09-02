package com.srimani.quickcart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * {@code LogoutServlet} handles user logout requests.
 * <p>
 * This servlet is responsible for invalidating the user's HTTP session,
 * effectively logging the user out of the application. After invalidating the
 * session, it redirects the user to the home page ("/").
 * </p>
 * 
 * @see HttpServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.invalidate();

		response.sendRedirect(request.getContextPath() + "/");
	}

}
