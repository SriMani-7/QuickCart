package com.srimani.quickcart.util;

import java.sql.DriverManager;

import com.srimani.quickcart.dao.BuyerDAO;
import com.srimani.quickcart.dao.OrderDAO;
import com.srimani.quickcart.dao.ProductDao;
import com.srimani.quickcart.dao.RetailerDAO;
import com.srimani.quickcart.dao.ReviewDAO;
import com.srimani.quickcart.dao.ShappingCartDAO;
import com.srimani.quickcart.dao.UserDAO;
import com.srimani.quickcart.dao.impl.DatabaseBuyerDAO;
import com.srimani.quickcart.dao.impl.DatabaseCartDAO;
import com.srimani.quickcart.dao.impl.DatabaseOrderDAO;
import com.srimani.quickcart.dao.impl.DatabaseProductDAO;
import com.srimani.quickcart.dao.impl.DatabaseRetailerDAO;
import com.srimani.quickcart.dao.impl.DatabaseReviewDAO;
import com.srimani.quickcart.dao.impl.DatabaseUserDAO;

public class DAOFactory {

	private DataSource dataSource = () -> {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommDb", "root", "Mani88");
	};

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return new DAOFactory();
	}

	public ProductDao getProductDAO() {
		return new DatabaseProductDAO(dataSource);
	}

	public UserDAO getUserDAO() {
		return new DatabaseUserDAO(dataSource);
	}

	public BuyerDAO getBuyerDAO() {
		return new DatabaseBuyerDAO(dataSource);
	}

	public ReviewDAO getReviewDAO() {
		return new DatabaseReviewDAO(dataSource);
	}

	public ShappingCartDAO getShappingCartDAO() {
		return new DatabaseCartDAO(dataSource);
	}

	public OrderDAO getOrderDAO() {
		// TODO Auto-generated method stub
		return new DatabaseOrderDAO(dataSource);
	}

	public RetailerDAO getRetailerDAO() {
		return new DatabaseRetailerDAO(dataSource);
	}

}
