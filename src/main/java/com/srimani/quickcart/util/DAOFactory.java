package com.srimani.quickcart.util;

import com.srimani.quickcart.dao.*;

public abstract class DAOFactory {

	public static DAOFactory getInstance() {
		return new DatabaseDAOFactory();
	}

	public abstract ProductDao getProductDAO();

    public abstract UserDAO getUserDAO();

    public abstract BuyerDAO getBuyerDAO();

    public abstract ReviewDAO getReviewDAO();

    public abstract ShappingCartDAO getShappingCartDAO();

    public abstract OrderDAO getOrderDAO();

    public abstract RetailerDAO getRetailerDAO();

}
