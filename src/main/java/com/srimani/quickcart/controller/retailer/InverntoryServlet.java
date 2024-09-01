package com.srimani.quickcart.controller.retailer;

import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.exception.ProductNotFoundException;
import com.srimani.quickcart.service.SellerService;
import com.srimani.quickcart.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class InverntoryServlet
 */
@WebServlet("/retailer/inventory")
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
		var pid = request.getParameter("productId");
		if (pid != null) {
			var ppid = Long.parseLong(pid);
            Product p = null;
            try {
                p = sellerService.getProduct(id, ppid);
            } catch (ProductNotFoundException e) {
                throw new RuntimeException(e);
            }
            List<ProductReview> reviesList = sellerService.getProductReviews(id);
			request.setAttribute("product", p);
			request.setAttribute("reviews", reviesList);
			request.getRequestDispatcher("/seller-views/productinfo.jsp").forward(request, response);
			return;
		}
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

        try {
            sellerService.deleteProduct(id, productId);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/retailer/inventory");
	}

}
