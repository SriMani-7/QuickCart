package com.srimani.quickcart.service;

import java.util.List;
import java.util.Optional;

import com.srimani.quickcart.dto.CartItem;
import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.entity.Order;
import com.srimani.quickcart.entity.OrderedProduct;
import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.entity.Review;

public interface BuyerService {

	List<Product> getProducts(String reqParameter, String category);

	Optional<Product> getProduct(long pId);

	boolean addReview(Review review);

	List<ProductReview> getProductReviews(long id);

	boolean addToCart(long userId, long productId);

	List<CartItem> getCart(long userId);

	boolean isProductInCart(Long userId, long id);

	boolean checkoutCart(Long userId, String city, String address, String pincode, String phoneNumber,
			String paymentMethod);

	List<Order> getBuyerOrders(long userId);

	Order getOrderDetails(long userId, long orderId);

	List<OrderedProduct> getOrderedProducts(long userId, long orderId);

	List<String> getProductCategories();

	Retailer getRetailerInfo(long id);

	boolean deleteCart(Long uid, long pid);

	void updateCartProductQuantity(Long uid, long pid, int quantity);

}
