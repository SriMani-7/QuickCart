package com.srimani.quickcart.controller.buyer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.util.ServiceFactory;

/**
 * Servlet implementation class ProductInfoServlet
 */
@WebServlet("/products/info")
public class ProductInfoServlet extends HttpServlet {
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
		var pid = request.getParameter("id");
		var userId = (Long) request.getSession().getAttribute("user-id");
		var id = Long.parseLong(pid);
		var p = service.getProduct(id);
		List<ProductReview> reviesList = service.getProductReviews(id);
		request.setAttribute("incart", service.isProductInCart(userId, id));
		request.setAttribute("product", p.orElseGet(() -> null));
		request.setAttribute("reviews", reviesList);
		request.getRequestDispatcher("/productinfo.jsp").forward(request, response);
	}
}
