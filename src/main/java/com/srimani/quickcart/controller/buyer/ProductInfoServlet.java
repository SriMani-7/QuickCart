package com.srimani.quickcart.controller.buyer;

import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ProductInfoServlet
 */
@WebServlet("/products/info")
public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BuyerService service;

	@Override
	public void init() throws ServletException {
		super.init();
		service = ServiceFactory.getBuyerService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var pid = request.getParameter("id");
		var userId = (Long) request.getSession().getAttribute("user-id");
		var id = Long.parseLong(pid);
		var p = service.getProduct(id);
		List<ProductReview> reviesList = service.getProductReviews(id);
		request.setAttribute("incart", userId == null ? false : service.isProductInCart(userId, id));
		request.setAttribute("product", p.orElseGet(() -> null));
		request.setAttribute("reviews", reviesList);
		request.setAttribute("retailer", service.getRetailerInfo(id));
		request.getRequestDispatcher("/productinfo.jsp").forward(request, response);
	}
}
