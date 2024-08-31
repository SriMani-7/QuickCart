package com.srimani.quickcart.controller.buyer;

import com.srimani.quickcart.service.CartService;
import com.srimani.quickcart.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CartService service;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		service = ServiceFactory.getCartService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		long userId = (long) session.getAttribute("user-id");
		var items = service.getCart(userId);
		double totalPrice = items.stream().mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getPrice()).sum();

		request.setAttribute("totalPrice", totalPrice);
		request.getRequestDispatcher("/checkout.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("user-id");

		// If user is not logged in, redirect to login page
		if (userId == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// Retrieve form data
		String city = request.getParameter("city");
		String address = request.getParameter("address");
		String pincode = request.getParameter("pincode");
		String phoneNumber = request.getParameter("phoneNumber");
		String paymentMethod = request.getParameter("paymentMethod");

		var orderSuccess = service.checkoutCart(userId, city, address, pincode, phoneNumber, paymentMethod);

		if (orderSuccess) {
			// Redirect to order confirmation page
			response.sendRedirect(request.getContextPath() + "/orders");
		} else {
			// Handle order failure
			request.setAttribute("error", "There was an issue processing your order. Please try again.");
			doGet(request, response);
		}
	}

}
