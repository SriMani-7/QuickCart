package com.srimani.quickcart.controller.admin;

import com.srimani.quickcart.dto.UserDTO;
import com.srimani.quickcart.exception.UserNotExistsException;
import com.srimani.quickcart.service.AdminService;
import com.srimani.quickcart.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ManageUserServlet
 */
@WebServlet("/admin/users")
public class ManageUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminService adminService;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		adminService = ServiceFactory.getAdminService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<UserDTO> users = adminService.getAllUsers();
		request.setAttribute("users", users);
		request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		long userId = Long.parseLong(request.getParameter("userId"));

		try {
			if ("block".equals(action)) {
				adminService.blockUser(userId);
			} else if ("delete".equals(action)) {
				adminService.deleteUser(userId);
			} else if ("activate".equals(action)) {
				adminService.activateUser(userId);
			}
		} catch (UserNotExistsException e) {
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/admin/users");
	}

}
