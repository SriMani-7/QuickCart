package com.srimani.quickcart.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.srimani.quickcart.dao.OrderDAO;
import com.srimani.quickcart.dao.ProductDao;
import com.srimani.quickcart.dao.ReviewDAO;
import com.srimani.quickcart.dao.ShappingCartDAO;
import com.srimani.quickcart.dto.CartItem;
import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.entity.Order;
import com.srimani.quickcart.entity.OrderedProduct;
import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.entity.Review;
import com.srimani.quickcart.util.DAOFactory;

public class BuyerServiceImpl implements BuyerService {

	private ProductDao productDao;
	private ReviewDAO reviewDAO;
	private ShappingCartDAO shappingCartDAO;
	private OrderDAO orderDAO;

	public BuyerServiceImpl(DAOFactory daoFactory) {
		productDao = daoFactory.getProductDAO();
		reviewDAO = daoFactory.getReviewDAO();
		shappingCartDAO = daoFactory.getShappingCartDAO();
		orderDAO = daoFactory.getOrderDAO();
	}

	@Override
	public List<Product> getProducts(String reqParameter, String category) {
		return productDao.findProducts(reqParameter, category);
	}

	@Override
	public Optional<Product> getProduct(long pId) {
		return productDao.getProduct(pId);
	}

	@Override
	public boolean addReview(Review review) {
		return reviewDAO.postReview(review) > 0;
	}

	@Override
	public List<ProductReview> getProductReviews(long id) {
		return reviewDAO.getProductReviews(id);
	}

	@Override
	public boolean addToCart(long userId, long productId) {
		return shappingCartDAO.addProduct(userId, productId) > 0;
	}

	@Override
	public LinkedList<CartItem> getCart(long userId) {
		return shappingCartDAO.getCartItemsForUserId(userId);
	}

	@Override
	public boolean isProductInCart(Long userId, long id) {
		return shappingCartDAO.isProductInCart(userId, id);
	}

	@Override
	public boolean checkoutCart(Long userId, String city, String address, String pincode, String phoneNumber,
			String paymentMethod) {
		return shappingCartDAO.checkoutCart(userId, city, address, pincode, phoneNumber, paymentMethod);

	}

	@Override
	public List<Order> getBuyerOrders(long userId) {
		return orderDAO.getBuyerOrders(userId);
	}

	@Override
	public Order getOrderDetails(long userId, long orderId) {
		return orderDAO.getOrderDetails(userId, orderId);
	}

	@Override
	public List<OrderedProduct> getOrderedProducts(long userId, long orderId) {
		return orderDAO.getOrderedProducts(userId, orderId);
	}

	@Override
	public List<String> getProductCategories() {
		return productDao.getCategories();
	}

	@Override
	public Retailer getRetailerInfo(long id) {
		return productDao.getRetailer(id);
	}
}
