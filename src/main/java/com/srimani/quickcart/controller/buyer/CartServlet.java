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
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
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

		request.setAttribute("cartItems", items);
		request.setAttribute("totalPrice", totalPrice);

		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var id = request.getParameter("productId");
		var action = request.getParameter("action");
		var uid = (Long) request.getSession().getAttribute("user-id");
		var pid = Long.parseLong(id);

		if (action.equals("add") && service.addToCart(uid, pid)) {
			response.sendRedirect(request.getContextPath() + "/products/info?id=" + pid);
			return;
		} else if (action.equals("delete")) {
			service.deleteCart(uid, pid);
		} else if (action.equals("update")) {
			var quantity = Integer.parseInt(request.getParameter("quantity"));
			service.updateCartProductQuantity(uid, pid, quantity);
		}
		response.sendRedirect(request.getContextPath() + "/cart");
	}

}
