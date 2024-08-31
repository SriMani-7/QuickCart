package com.srimani.quickcart.util;

import com.srimani.quickcart.dao.*;
import com.srimani.quickcart.dao.impl.*;

import java.sql.DriverManager;

public class DatabaseDAOFactory extends DAOFactory {
    private DataSource dataSource = () -> {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommDb", "root", "Mani88");
    };

    DatabaseDAOFactory() {

    }

    @Override
    public ProductDao getProductDAO() {
        return new DatabaseProductDAO(dataSource);
    }

    @Override
    public UserDAO getUserDAO() {
        return new DatabaseUserDAO(dataSource);
    }

    @Override
    public BuyerDAO getBuyerDAO() {
        return new DatabaseBuyerDAO(dataSource);
    }

    @Override
    public ReviewDAO getReviewDAO() {
        return new DatabaseReviewDAO(dataSource);
    }

    @Override
    public ShappingCartDAO getShappingCartDAO() {
        return new DatabaseCartDAO(dataSource);
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new DatabaseOrderDAO(dataSource);
    }

    @Override
    public RetailerDAO getRetailerDAO() {
        return new DatabaseRetailerDAO(dataSource);
    }
}
