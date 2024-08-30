package com.srimani.quickcart.dao;

import java.util.List;

import com.srimani.quickcart.dto.ProductOrderDetail;
import com.srimani.quickcart.entity.Order;
import com.srimani.quickcart.entity.OrderedProduct;

public interface OrderDAO {

	List<Order> getBuyerOrders(long userId);

	Order getOrderDetails(long userId, long orderId);

	List<OrderedProduct> getOrderedProducts(long userId, long orderId);

	List<ProductOrderDetail> getSellerOrders(Long id);

	boolean updateProductOrderStatus(Long sellerId, long orderId, long productId, String status);

}
