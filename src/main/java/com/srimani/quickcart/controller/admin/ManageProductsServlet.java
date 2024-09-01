package com.srimani.quickcart.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.service.AdminService;
import com.srimani.quickcart.util.ServiceFactory;

/**
 * Servlet implementation class ManageProductsServlet
 */
@WebServlet("/admin/products")
public class ManageProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AdminService adminService;

	@Override
	public void init() throws ServletException {
		super.init();
		adminService = ServiceFactory.getAdminService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		var pr = adminService.getAllProducts();

		System.out.println(pr.toString());
		request.setAttribute("products", pr);
		request.getRequestDispatcher("/admin/products.jsp").forward(request, response);
	}
}
