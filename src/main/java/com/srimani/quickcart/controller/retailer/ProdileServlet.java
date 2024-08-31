package com.srimani.quickcart.controller.retailer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.service.SellerService;
import com.srimani.quickcart.util.ServiceFactory;

@WebServlet("/retailer/profile")
public class ProdileServlet extends HttpServlet {
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
		var uid = (Long) session.getAttribute("user-id");
		var re = sellerService.getProfile(uid);
		request.setAttribute("retailer", re);
		request.getRequestDispatcher("/seller-views/profile.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve the retailer ID from the session
		var session = request.getSession();
		var uid = (Long) session.getAttribute("user-id");

		// Retrieve form parameters
		String name = request.getParameter("name");
		String contactEmail = request.getParameter("contactEmail");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");

		// Update retailer profile
		Retailer retailer = new Retailer();
		retailer.setUserId(uid); // Set the retailer ID
		retailer.setName(name);
		retailer.setContactEMail(contactEmail);
		retailer.setAddress(address);
		retailer.setPhoneNumber(phoneNumber);

		// Update the retailer profile in the database
		sellerService.updateProfile(retailer);

		// Optionally, add a success message to the request or session
		request.setAttribute("message", "Profile updated successfully!");

		// Redirect to the profile page to show the updated details
		response.sendRedirect(request.getContextPath() + "/retailer/profile");
	}

}
