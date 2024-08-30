package com.srimani.quickcart.controller.retailer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.dto.ProductOrderDetail;
import com.srimani.quickcart.service.SellerService;
import com.srimani.quickcart.util.ServiceFactory;

/**
 * Servlet implementation class OrdersServlet
 */
@WebServlet("/retailer/orders")
public class OrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private SellerService sellerService;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		sellerService = ServiceFactory.getSellerService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var session = request.getSession();
		var id = (Long) session.getAttribute("user-id");

		List<ProductOrderDetail> ordersDetails = sellerService.getOrders(id);
		request.setAttribute("orders", ordersDetails);
		request.getRequestDispatcher("/seller-views/orders.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var session = request.getSession();
		var id = (Long) session.getAttribute("user-id");
		long orderId = Long.parseLong(request.getParameter("orderId"));
		long productId = Long.parseLong(request.getParameter("productId"));
		String status = request.getParameter("status");

		sellerService.updateOrderStatus(id, orderId, productId, status);

		response.sendRedirect(request.getContextPath() + "/retailer/orders");
	}

}
