package com.srimani.quickcart.controller.buyer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.util.ServiceFactory;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BuyerService service;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		service = ServiceFactory.getBuyerService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchQuery = request.getParameter("search");
		var category = request.getParameter("category");
		var products = service.getProducts(searchQuery, category);
		var categories = service.getProductCategories();
		request.setAttribute("products", products);
		request.setAttribute("categories", categories);
		System.out.println("Forwarding to products.jsp");
		request.getRequestDispatcher("products.jsp").forward(request, response);
	}

}
