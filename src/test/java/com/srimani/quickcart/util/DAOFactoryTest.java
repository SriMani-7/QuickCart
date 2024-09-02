package com.srimani.quickcart.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.srimani.quickcart.dao.BuyerDAO;
import com.srimani.quickcart.dao.OrderDAO;
import com.srimani.quickcart.dao.ProductDao;
import com.srimani.quickcart.dao.RetailerDAO;
import com.srimani.quickcart.dao.ReviewDAO;
import com.srimani.quickcart.dao.ShappingCartDAO;
import com.srimani.quickcart.dao.UserDAO;
import com.srimani.quickcart.util.DAOFactory;
import com.srimani.quickcart.util.DatabaseDAOFactory;

public class DAOFactoryTest {
	private DAOFactory daoFactory;

	@Before
	public void setUp() {
		daoFactory = DAOFactory.getInstance();
	}

	@Test
	public void testGetInstance() {
		assertNotNull(daoFactory);
		assertTrue(daoFactory instanceof DatabaseDAOFactory);
	}

	@Test
	public void testGetProductDAO() {
		ProductDao productDao = daoFactory.getProductDAO();
		assertNotNull(productDao);
	}

	@Test
	public void testGetUserDAO() {
		UserDAO userDao = daoFactory.getUserDAO();
		assertNotNull(userDao);
	}

	@Test
	public void testGetBuyerDAO() {
		BuyerDAO buyerDao = daoFactory.getBuyerDAO();
		assertNotNull(buyerDao);
	}

	@Test
	public void testGetReviewDAO() {
		ReviewDAO reviewDao = daoFactory.getReviewDAO();
		assertNotNull(reviewDao);
	}

	@Test
	public void testGetShoppingCartDAO() {
		ShappingCartDAO shoppingCartDao = daoFactory.getShappingCartDAO();
		assertNotNull(shoppingCartDao);
	}

	@Test
	public void testGetOrderDAO() {
		OrderDAO orderDao = daoFactory.getOrderDAO();
		assertNotNull(orderDao);
	}

	@Test
	public void testGetRetailerDAO() {
		RetailerDAO retailerDao = daoFactory.getRetailerDAO();
		assertNotNull(retailerDao);
	}
}
