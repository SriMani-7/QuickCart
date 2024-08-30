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
 * Servlet implementation class ProductEditServlet
 */
@WebServlet("/seller/inventory/edit")
public class ProductEditServlet extends HttpServlet {
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
		var session = request.getSession();
		var id = (Long) session.getAttribute("user-id");
		var pid = request.getParameter("id");
		var product = sellerService.getProduct(id, Long.parseLong(pid));
		request.setAttribute("product", product);
		request.getRequestDispatcher("/seller-views/edit-product.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var session = request.getSession();
		var id = (Long) session.getAttribute("user-id");

		var name = request.getParameter("name");
		var description = request.getParameter("description");
		var category = request.getParameter("category");
		var price = request.getParameter("price");
		var pid = request.getParameter("id");
		var imageUrl = request.getParameter("imageUrl");

		var p = new Product();
		p.setId(Long.parseLong(pid));
		p.setName(name);
		p.setDescription(description);
		p.setCategory(category);
		p.setPrice(Double.parseDouble(price));
		p.setImageUrl(imageUrl);

		if (sellerService.updateProduct(id, p)) {
			response.sendRedirect(request.getContextPath() + "/seller/inventory");
		} else {
			request.setAttribute("error", "Something went wrong");
			doGet(request, response);
		}

	}
}
