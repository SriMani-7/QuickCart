package com.srimani.quickcart.service;

import java.util.List;

import com.srimani.quickcart.dao.ProductDao;
import com.srimani.quickcart.dao.UserDAO;
import com.srimani.quickcart.dto.ProductManagementDTO;
import com.srimani.quickcart.dto.UserDTO;
import com.srimani.quickcart.exception.UserNotExistsException;
import com.srimani.quickcart.util.DAOFactory;

public class AdminServiceImpl implements AdminService {

	private UserDAO userDao;
	private ProductDao productDao;

	public AdminServiceImpl(DAOFactory instance) {
		// TODO Auto-generated constructor stub
		userDao = instance.getUserDAO();
		productDao = instance.getProductDAO();
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public void blockUser(long userId) throws UserNotExistsException {
		userDao.manageUser(userId, "BLOCK");
	}

	@Override
	public void deleteUser(long userId) throws UserNotExistsException {
		userDao.deleteUser(userId);
	}

	@Override
	public void activateUser(long userId) throws UserNotExistsException {
		userDao.manageUser(userId, "ACTIVE");

	}

	@Override
	public List<ProductManagementDTO> getAllProducts() {
		return productDao.getAllProductsForAdmin();
	}

}
