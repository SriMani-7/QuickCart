package com.srimani.quickcart.service;

import java.util.List;

import com.srimani.quickcart.dao.OrderDAO;
import com.srimani.quickcart.dao.ProductDao;
import com.srimani.quickcart.dao.ReviewDAO;
import com.srimani.quickcart.dto.ProductOrderDetail;
import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.util.DAOFactory;

public class SellerServiceImpl implements SellerService {

	private ProductDao productDao;
	private OrderDAO orderDAO;
	private ReviewDAO reviewDAO;

	public SellerServiceImpl(DAOFactory factory) {
		productDao = factory.getProductDAO();
		orderDAO = factory.getOrderDAO();
		reviewDAO = factory.getReviewDAO();
	}

	@Override
	public List<Product> getProducts(Long id) {
		return productDao.getProductsByRetailer(id);
	}

	@Override
	public boolean addProduct(Long id, Product p) {
		return productDao.addProduct(id, p);
	}

	@Override
	public Product getProduct(Long sellerId, long pId) {
		return productDao.getProduct(sellerId, pId);
	}

	@Override
	public boolean updateProduct(Long id, Product p) {
		return productDao.updateProduct(id, p);
	}

	@Override
	public void deleteProduct(Long sellerId, long productId) {
		productDao.deleteProduct(sellerId, productId);
	}

	@Override
	public List<ProductOrderDetail> getOrders(Long id) {
		return orderDAO.getSellerOrders(id);
	}

	@Override
	public boolean updateOrderStatus(Long sellerId, long orderId, long productId, String status) {
		return orderDAO.updateProductOrderStatus(sellerId, orderId, productId, status);
	}

	@Override
	public List<ProductReview> getProductReviews(Long id) {
		return reviewDAO.getProductReviews(id);
	}

}
