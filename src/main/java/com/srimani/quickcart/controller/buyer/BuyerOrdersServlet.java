package com.srimani.quickcart.controller.buyer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.util.ServiceFactory;

/**
 * Servlet implementation class BuyerOrdersServlet
 */
@WebServlet("/orders")
public class BuyerOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BuyerService service;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		service = ServiceFactory.getBuyerService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user-id") == null) {
			// User is not logged in, redirect to login page
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		Long userId = (Long) session.getAttribute("user-id");

		// Fetch the list of orders for the user
		var orders = service.getBuyerOrders(userId);

		// Set the orders as a request attribute
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/orders.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
