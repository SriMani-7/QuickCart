package com.srimani.quickcart.service;

import com.srimani.quickcart.dao.BuyerDAO;
import com.srimani.quickcart.dao.RetailerDAO;
import com.srimani.quickcart.dao.UserDAO;
import com.srimani.quickcart.entity.Buyer;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.entity.User;
import com.srimani.quickcart.util.DAOFactory;

public class AuthenticationServiceImpl implements AuthenticationService {
	private UserDAO userDAO;
	private BuyerDAO buyerDAO;
	private RetailerDAO retailerDAO;

	public AuthenticationServiceImpl(DAOFactory daoFactory) {
		this.userDAO = daoFactory.getUserDAO();
		this.buyerDAO = daoFactory.getBuyerDAO();
		this.retailerDAO = daoFactory.getRetailerDAO();
	}

	@Override
	public boolean hasUsername(String username) {
		return userDAO.hasUsername(username);
	}

	@Override
	public long createUser(User user) {
		return userDAO.createUser(user);
	}

	@Override
	public void createBuyer(Buyer buyer) {
		buyerDAO.createBuyer(buyer);
	}

	@Override
	public User authenticate(String username, String password) {
		var u = userDAO.getUserByUsername(username);
		if (u.getPassword().equals(password)) {
			return u;
		} else
			return null;
	}

	@Override
	public void createRetailer(Retailer s) {
		retailerDAO.createRetailer(s);
	}
}
