package com.srimani.quickcart.controller.buyer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.srimani.quickcart.entity.Review;
import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.util.ServiceFactory;

/**
 * Servlet implementation class ProductReviewServlet
 */
@WebServlet("/reviews")
public class ProductReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BuyerService service;

	@Override
	public void init() throws ServletException {
		super.init();
		service = ServiceFactory.getBuyerService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var uid = (Long) req.getSession().getAttribute("user-id");
		var reviews = service.getBuyerProductReviews(uid);
		req.setAttribute("userReviews", reviews);
		req.getRequestDispatcher("reviews.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var action = request.getParameter("action");
		if (action.equals("delete")) {
			doDelete(request, response);
			return;
		}
		long productId = Long.parseLong(request.getParameter("productId"));
		String reviewText = request.getParameter("reviewText");
		int rating = Integer.parseInt(request.getParameter("rating"));

		// Get the user ID from the session
		HttpSession session = request.getSession();
		long userId = (Long) session.getAttribute("user-id");

		// Create and save the review
		Review review = new Review();
		review.setProductId(productId);
		review.setUserId(userId);
		review.setMessage(reviewText);
		review.setRating(rating);

		service.addReview(review);

		// Redirect back to the product page
		response.sendRedirect(request.getContextPath() + "/products/info?id=" + productId);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		long rId = Long.parseLong(request.getParameter("productId"));
		var uid = (Long) request.getSession().getAttribute("user-id");
		service.deleteReview(rId, uid);

		resp.sendRedirect(request.getContextPath() + "/reviews");
	}

}
