package com.srimani.quickcart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.srimani.quickcart.dao.OrderDAO;
import com.srimani.quickcart.dto.ProductOrderDetail;
import com.srimani.quickcart.entity.Order;
import com.srimani.quickcart.entity.OrderedProduct;
import com.srimani.quickcart.util.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseOrderDAO implements OrderDAO {

	private DataSource dataSource;
	private final Logger logger = LogManager.getLogger();

	public DatabaseOrderDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Order> getBuyerOrders(long userId) {
		List<Order> orders = new ArrayList<>();

		String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";

		try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getLong("order_id"));
					order.setUserId(rs.getLong("user_id"));
					order.setTotalPrice(rs.getDouble("total_price"));
					order.setOrderDate(rs.getTimestamp("order_date"));
					order.setPaymentMethod(rs.getString("payment_method"));
					order.setShippingAddress(rs.getString("shipping_address"));
					order.setCity(rs.getString("city"));
					order.setPincode(rs.getInt("pincode"));
					order.setPhoneNumber(rs.getString("phone_number"));
					order.setStatus(rs.getString("status"));

					orders.add(order);
				}
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return orders;
	}

	@Override
	public Order getOrderDetails(long userId, long orderId) {
		Order order = null;

		String sql = "SELECT * FROM orders WHERE user_id = ? AND order_id = ?";

		try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, userId);
			ps.setLong(2, orderId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					order = new Order();
					order.setOrderId(rs.getLong("order_id"));
					order.setUserId(rs.getLong("user_id"));
					order.setTotalPrice(rs.getDouble("total_price"));
					order.setOrderDate(rs.getTimestamp("order_date"));
					order.setPaymentMethod(rs.getString("payment_method"));
					order.setShippingAddress(rs.getString("shipping_address"));
					order.setCity(rs.getString("city"));
					order.setPincode(rs.getInt("pincode"));
					order.setPhoneNumber(rs.getString("phone_number"));
					order.setStatus(rs.getString("status"));
				}
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return order;
	}

	@Override
	public List<OrderedProduct> getOrderedProducts(long userId, long orderId) {
		List<OrderedProduct> orderedProducts = new ArrayList<>();

		String sql = """
				SELECT p.id as product_id, p.name as product_name, op.quantity, op.price, op.status
				FROM order_items op JOIN products p ON op.product_id = p.id
				JOIN orders o ON op.order_id = o.order_id WHERE o.user_id = ? AND o.order_id = ?
				""";

		try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, userId);
			ps.setLong(2, orderId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					OrderedProduct orderedProduct = new OrderedProduct();
					orderedProduct.setOrderId(orderId);
					orderedProduct.setProductId(rs.getLong("product_id"));
					orderedProduct.setName(rs.getString("product_name"));
					orderedProduct.setQuantity(rs.getInt("quantity"));
					orderedProduct.setPrice(rs.getDouble("price"));
					orderedProduct.setStatus(rs.getString("status"));

					orderedProducts.add(orderedProduct);
				}
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return orderedProducts;
	}

	@Override
	public List<ProductOrderDetail> getSellerOrders(Long retailerId) {
		List<ProductOrderDetail> orderDetails = new ArrayList<>();

		String query = """
				    SELECT
				        o.order_id, oi.product_id, p.name, oi.quantity, oi.price,
				        o.user_id AS buyer_id, o.shipping_address, o.city, o.pincode, o.phone_number,
				        o.order_date, o.payment_method, oi.status
				    FROM
				        orders o
				    JOIN
				        order_items oi ON o.order_id = oi.order_id
				    JOIN
				        products p ON oi.product_id = p.id
				    WHERE
				        p.retailer_id = ?
				    ORDER BY
				        o.order_date DESC
				""";

		try (var con = dataSource.getConnection(); var stmt = con.prepareStatement(query)) {

			stmt.setLong(1, retailerId);

			try (var rs = stmt.executeQuery()) {
				while (rs.next()) {
					ProductOrderDetail orderDetail = new ProductOrderDetail();
					orderDetail.setOrderId(rs.getLong("order_id"));
					orderDetail.setProductId(rs.getLong("product_id"));
					orderDetail.setName(rs.getString("name"));
					orderDetail.setQuantity(rs.getInt("quantity"));
					orderDetail.setPrice(rs.getDouble("price"));
					orderDetail.setBuyerId(rs.getLong("buyer_id"));
					orderDetail.setShippingAddress(rs.getString("shipping_address"));
					orderDetail.setCity(rs.getString("city"));
					orderDetail.setPincode(rs.getInt("pincode"));
					orderDetail.setPhoneNumber(rs.getString("phone_number"));
					orderDetail.setOrderDate(rs.getTimestamp("order_date"));
					orderDetail.setPaymentMode(rs.getString("payment_method"));
					orderDetail.setStatus(rs.getString("status"));

					orderDetails.add(orderDetail);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

		return orderDetails;
	}

	@Override
	public boolean updateProductOrderStatus(Long sellerId, long orderId, long productId, String status) {
		String query = """
				    UPDATE order_items oi
				    JOIN products p ON oi.product_id = p.id
				    SET oi.status = ?
				    WHERE oi.order_id = ?
				    AND oi.product_id = ?
				    AND p.retailer_id = ?
				""";

		try (var con = dataSource.getConnection(); var stmt = con.prepareStatement(query)) {

			// Set the parameters for the prepared statement
			stmt.setString(1, status); // Set the new status
			stmt.setLong(2, orderId); // Set the order ID
			stmt.setLong(3, productId); // Set the product ID
			stmt.setLong(4, sellerId); // Set the seller ID

			// Execute the update
			int rowsAffected = stmt.executeUpdate();

			// Return true if one or more rows were updated, false otherwise
			return rowsAffected > 0;

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

}