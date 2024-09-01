package com.srimani.quickcart.controller.buyer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.srimani.quickcart.entity.OrderedProduct;
import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.util.ServiceFactory;

/**
 * Servlet implementation class OrderInfoServlet
 */
@WebServlet("/orders/info")
public class OrderInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BuyerService service;

	@Override
	public void init() throws ServletException {
		super.init();
		service = ServiceFactory.getBuyerService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderIdParam = request.getParameter("id");
		HttpSession session = request.getSession();
		long userId = (long) session.getAttribute("user-id");

		if (orderIdParam != null && userId != 0) {
			long orderId = Long.parseLong(orderIdParam);

			try {
				// Retrieve order details
				var order = service.getOrderDetails(userId, orderId);
				if (order != null) {
					// Retrieve ordered products
					List<OrderedProduct> orderedProducts = service.getOrderedProducts(userId, orderId);

					// Set attributes and forward to JSP
					request.setAttribute("order", order);
					request.setAttribute("orderedProducts", orderedProducts);
					request.getRequestDispatcher("/order-info.jsp").forward(request, response);
				} else {
					response.sendRedirect(request.getContextPath() + "/orders");
				}
			} catch (NumberFormatException e) {
				response.sendRedirect(request.getContextPath() + "/orders");
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/orders");
		}
	}

}
