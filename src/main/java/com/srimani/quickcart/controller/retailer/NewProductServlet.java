package com.srimani.quickcart.controller.retailer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.service.SellerService;
import com.srimani.quickcart.util.ServiceFactory;

/**
 * Servlet implementation class NewProductServlet
 */
@WebServlet("/seller/inventory/add")
public class NewProductServlet extends HttpServlet {
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
		// TODO Auto-generated method stub

		request.getRequestDispatcher("/seller-views/add-product.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var session = request.getSession();
		var id = (Long) session.getAttribute("user-id");

		var name = request.getParameter("name");
		var description = request.getParameter("description");
		var category = request.getParameter("category");
		var price = request.getParameter("price");

		var p = new Product();
		p.setName(name);
		p.setDescription(description);
		p.setCategory(category);
		p.setPrice(Double.parseDouble(price));

		if (sellerService.addProduct(id, p)) {
			response.sendRedirect(request.getContextPath() + "/seller/inventory");
		} else {
			request.setAttribute("error", "Something went wrong");
			doGet(request, response);
		}

	}

}
