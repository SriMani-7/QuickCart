package com.srimani.quickcart.util;

import java.sql.DriverManager;

import com.srimani.quickcart.dao.DatabaseProductDAO;
import com.srimani.quickcart.dao.ProductDao;

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

}
