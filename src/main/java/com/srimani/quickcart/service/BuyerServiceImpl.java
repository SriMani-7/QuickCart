package com.srimani.quickcart.service;

import java.util.List;
import java.util.Optional;

import com.srimani.quickcart.dao.ProductDao;
import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.util.DAOFactory;

public class BuyerServiceImpl implements BuyerService {

	private ProductDao productDao;

	public BuyerServiceImpl(DAOFactory daoFactory) {
		productDao = daoFactory.getProductDAO();
	}

	@Override
	public List<Product> getProducts(String reqParameter) {
		return productDao.findProducts(reqParameter);
	}

	@Override
	public Optional<Product> getProduct(long pId) {
		return productDao.getProduct(pId);
	}
}
