package com.srimani.quickcart.util;

import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

/**
 * {@code DatabaseDAOFactory} is a concrete implementation of the
 * {@link DAOFactory} class. It provides methods to obtain various {@code DAO}
 * implementations that interact with a MySQL database.
 * <p>
 * This factory initializes a {@code DataSource} for connecting to the database
 * and provides methods to get instances of DAOs for different entities such as
 * products, users, orders, etc.
 * </p>
 */
public class DatabaseDAOFactory extends DAOFactory {
	private final DataSource dataSource = () -> {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			Logger logger = LogManager.getLogger();
			logger.error(e.getMessage(), e);
		}
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommDb", "root", "Mani88");
	};

	/**
	 * Creates a new instance of {@code DatabaseDAOFactory}.
	 * <p>
	 * The constructor initializes the data source for connecting to the MySQL
	 * database.
	 * </p>
	 */
	DatabaseDAOFactory() {

	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns an instance of {@code DatabaseProductDAO} that uses the
	 * configured {@code DataSource} to perform database operations related to
	 * products.
	 * </p>
	 * 
	 * @return an instance of {@code DatabaseProductDAO}.
	 */
	@Override
	public ProductDao getProductDAO() {
		return new DatabaseProductDAO(dataSource);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns an instance of {@code DatabaseUserDAO} that uses the
	 * configured {@code DataSource} to perform database operations related to
	 * users.
	 * </p>
	 * 
	 * @return an instance of {@code DatabaseUserDAO}.
	 */
	@Override
	public UserDAO getUserDAO() {
		return new DatabaseUserDAO(dataSource);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns an instance of {@code DatabaseBuyerDAO} that uses the
	 * configured {@code DataSource} to perform database operations related to
	 * buyers.
	 * </p>
	 * 
	 * @return an instance of {@code DatabaseBuyerDAO}.
	 */
	@Override
	public BuyerDAO getBuyerDAO() {
		return new DatabaseBuyerDAO(dataSource);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns an instance of {@code DatabaseReviewDAO} that uses the
	 * configured {@code DataSource} to perform database operations related to
	 * reviews.
	 * </p>
	 * 
	 * @return an instance of {@code DatabaseReviewDAO}.
	 */
	@Override
	public ReviewDAO getReviewDAO() {
		return new DatabaseReviewDAO(dataSource);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns an instance of {@code DatabaseCartDAO} that uses the
	 * configured {@code DataSource} to perform database operations related to
	 * shopping carts.
	 * </p>
	 * 
	 * @return an instance of {@code DatabaseCartDAO}.
	 */
	@Override
	public ShappingCartDAO getShappingCartDAO() {
		return new DatabaseCartDAO(dataSource);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns an instance of {@code DatabaseOrderDAO} that uses the
	 * configured {@code DataSource} to perform database operations related to
	 * orders.
	 * </p>
	 * 
	 * @return an instance of {@code DatabaseOrderDAO}.
	 */
	@Override
	public OrderDAO getOrderDAO() {
		return new DatabaseOrderDAO(dataSource);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns an instance of {@code DatabaseRetailerDAO} that uses the
	 * configured {@code DataSource} to perform database operations related to
	 * retailers.
	 * </p>
	 * 
	 * @return an instance of {@code DatabaseRetailerDAO}.
	 */
	@Override
	public RetailerDAO getRetailerDAO() {
		return new DatabaseRetailerDAO(dataSource);
	}
}
