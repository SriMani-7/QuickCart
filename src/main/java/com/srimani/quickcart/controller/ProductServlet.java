package com.srimani.quickcart.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.service.BuyerServiceImpl;
import com.srimani.quickcart.util.DAOFactory;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BuyerService service;

	public ProductServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		service = new BuyerServiceImpl(DAOFactory.getInstance());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var path = request.getPathInfo();
		System.out.println("Received request for path: " + path);

		if (path == null || path.equals("/")) {
			String searchQuery = request.getParameter("search");
			List<Product> products = service.getProducts(searchQuery);
			request.setAttribute("products", products);
			System.out.println("Forwarding to products.jsp");
			request.getRequestDispatcher("products.jsp").forward(request, response);
		}

	}

}
