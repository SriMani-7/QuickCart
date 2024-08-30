package com.srimani.quickcart.controller.retailer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.service.SellerService;
import com.srimani.quickcart.util.ServiceFactory;

/**
 * Servlet implementation class InverntoryServlet
 */
@WebServlet("/seller/inventory")
public class InverntoryServlet extends HttpServlet {
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
		List<Product> products = sellerService.getProducts(id);
		request.setAttribute("inventory", products);
		request.getRequestDispatcher("/seller-views/inventory.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("_method");

		if ("DELETE".equalsIgnoreCase(method)) {
			doDelete(request, response);
		} else {
			// Handle other POST operations, if any
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long productId = Long.parseLong(req.getParameter("id"));
		var session = req.getSession();
		var id = (Long) session.getAttribute("user-id");

		sellerService.deleteProduct(id, productId);

		resp.sendRedirect(req.getContextPath() + "/seller/inventory");
	}

}
