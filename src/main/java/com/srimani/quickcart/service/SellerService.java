package com.srimani.quickcart.service;

import java.util.List;

import com.srimani.quickcart.dto.ProductOrderDetail;
import com.srimani.quickcart.entity.Product;

public interface SellerService {

	List<Product> getProducts(Long id);

	boolean addProduct(Long id, Product p);

	Product getProduct(Long sellerId, long pId);

	boolean updateProduct(Long id, Product p);

	void deleteProduct(Long sellerId, long productId);

	List<ProductOrderDetail> getOrders(Long id);

	boolean updateOrderStatus(Long id, long orderId, long productId, String status);

}
