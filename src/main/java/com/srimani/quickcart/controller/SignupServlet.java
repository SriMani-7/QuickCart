package com.srimani.quickcart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.srimani.quickcart.entity.Buyer;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.entity.User;
import com.srimani.quickcart.service.AuthenticationService;
import com.srimani.quickcart.util.ServiceFactory;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AuthenticationService service;

	@Override
	public void init() throws ServletException {
		super.init();
		service = ServiceFactory.getAuthenticationService();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/signup.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String userType = request.getParameter("userType").toUpperCase();

		if (service.hasUsername(username)) {
			request.setAttribute("error", "Username is already exists");
			doGet(request, response);
		} else {
			User u = new User(username, password, userType, email);
			var uid = service.createUser(u);
			switch (userType) {
			case "BUYER": {
				var b = new Buyer();
				b.setPhoneNumber(phone);
				b.setUserId(uid);
				service.createBuyer(b);
				response.sendRedirect(request.getContextPath() + "/login");
				break;
			}
			case "SELLER": {
				var s = new Retailer();
				s.setPhoneNumber(phone);
				s.setContactEMail(email);
				s.setUserId(uid);
				s.setName(username);
				service.createRetailer(s);
				response.sendRedirect(request.getContextPath() + "/login");
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + userType);
			}

		}
	}

}
