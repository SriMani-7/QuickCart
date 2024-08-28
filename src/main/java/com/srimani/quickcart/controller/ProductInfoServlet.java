package com.srimani.quickcart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.service.BuyerServiceImpl;
import com.srimani.quickcart.util.DAOFactory;

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
		service = new BuyerServiceImpl(DAOFactory.getInstance());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var pid = request.getParameter("id");
		var id = Long.parseLong(pid);
		var p = service.getProduct(id);
		request.setAttribute("product", p.orElseGet(() -> null));
		request.getRequestDispatcher("/productinfo.jsp").forward(request, response);
	}
}
