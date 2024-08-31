package com.srimani.quickcart.service;

import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.entity.*;

import java.util.List;
import java.util.Optional;

public interface BuyerService {

	List<Product> getProducts(String reqParameter, String category);

	Optional<Product> getProduct(long pId);

	boolean addReview(Review review);

	List<ProductReview> getProductReviews(long id);

	boolean isProductInCart(Long userId, long id);

	List<Order> getBuyerOrders(long userId);

	Order getOrderDetails(long userId, long orderId);

	List<OrderedProduct> getOrderedProducts(long userId, long orderId);

	List<String> getProductCategories();

	Retailer getRetailerInfo(long id);

	List<Review> getBuyerProductReviews(Long uid);

	void deleteReview(long rId, long userId);

}
