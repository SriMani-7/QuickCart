package com.srimani.quickcart.service;

import java.util.List;

import com.srimani.quickcart.dto.ProductOrderDetail;
import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.exception.ProductNotFoundException;

public interface SellerService {

	List<Product> getProducts(Long id);

	boolean addProduct(Long id, Product p);

	Product getProduct(Long sellerId, long pId) throws ProductNotFoundException;

	boolean updateProduct(Long id, Product p);

	void deleteProduct(Long sellerId, long productId) throws ProductNotFoundException;

	List<ProductOrderDetail> getOrders(Long id);

	boolean updateOrderStatus(Long id, long orderId, long productId, String status);

	List<ProductReview> getProductReviews(Long id);

	Retailer getProfile(Long uid);

	void updateProfile(Retailer retailer);

}
