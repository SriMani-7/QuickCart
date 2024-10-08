package com.srimani.quickcart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.srimani.quickcart.dao.ShappingCartDAO;
import com.srimani.quickcart.dto.CartItem;
import com.srimani.quickcart.util.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseCartDAO implements ShappingCartDAO {

	private DataSource dataSource;
	private final Logger logger = LogManager.getLogger();

	public DatabaseCartDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int addProduct(long userId, long productId) {
		return dataSource.withQuery("insert into cart_items (buyer_id,product_id, quantity) values(?,?,1)", st -> {
			try {
				st.setLong(1, userId);
				st.setLong(2, productId);
				return st.executeUpdate();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				return 0;
			}
		});
	}

	@Override
	public LinkedList<CartItem> getCartItemsForUserId(long userId) {
		var query = "select p.name, p.price, p.description, ci.quantity, p.id  from products p inner join cart_items ci on ci.product_id = p.id where ci.buyer_id = ?";
		return dataSource.withQuery(query, st -> {
			try {
				st.setLong(1, userId);
				var set = st.executeQuery();
				var items = new LinkedList<CartItem>();
				while (set.next()) {
					var c = new CartItem();
					c.setProductId(set.getLong(5));
					c.setProductName(set.getString(1));
					c.setPrice(set.getDouble(2));
					c.setDescription(set.getString(3));
					c.setQuantity(set.getInt(4));
					items.add(c);
				}
				return items;
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				return new LinkedList<>();
			}
		});
	}

	@Override
	public boolean isProductInCart(Long userId, long id) {
		try (var con = dataSource.getConnection()) {
			var st = con.prepareStatement("select product_id from cart_items where product_id = ? and buyer_id = ?");
			st.setLong(1, id);
			st.setLong(2, userId);

			var set = st.executeQuery();
			return set.next();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public boolean checkoutCart(Long userId, String city, String address, String pincode, String phoneNumber,
			String paymentMethod) {
		try (var con = dataSource.getConnection()) {
			logger.debug("checkout cart: disabling auto commit and begin transaction");
			con.setAutoCommit(false); // Begin transaction

			try {
				// Calculate the total price
				List<CartItem> items = getCartItemsForUserId(userId);
				double totalPrice = items.stream().mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getPrice())
						.sum();
                logger.debug("No of items in the cart {}", items.size());

				// Insert into orders table
				String orderQuery = "INSERT INTO orders (user_id, total_price, payment_method, shipping_address, city, pincode, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement orderStmt = con.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
				orderStmt.setLong(1, userId);
				orderStmt.setDouble(2, totalPrice);
				orderStmt.setString(3, paymentMethod);
				orderStmt.setString(4, address);
				orderStmt.setString(5, city);
				orderStmt.setString(6, pincode);
				orderStmt.setString(7, phoneNumber);
				orderStmt.executeUpdate();

				// Get the generated order ID
				ResultSet rs = orderStmt.getGeneratedKeys();
				if (!rs.next()) {
					throw new SQLException("Failed to create order, no ID obtained.");
				}
				long orderId = rs.getLong(1);
                logger.debug("Order id {}", orderId);

				// Insert into order_items table
				String orderItemsQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
				PreparedStatement orderItemsStmt = con.prepareStatement(orderItemsQuery);
				logger.debug("Adding cart items to batch");
				for (CartItem item : items) {
					orderItemsStmt.setLong(1, orderId);
					orderItemsStmt.setLong(2, item.getProductId());
					orderItemsStmt.setInt(3, item.getQuantity());
					orderItemsStmt.setDouble(4, item.getPrice());
					orderItemsStmt.addBatch(); // Add to batch for efficiency
				}

				var rows = orderItemsStmt.executeBatch();
				logger.debug("Batch execution results "+ Arrays.toString(rows));
				// Delete the items from the cart after checkout
				String deleteCartItemsQuery = "DELETE FROM cart_items WHERE buyer_id = ?";
				PreparedStatement deleteCartItemsStmt = con.prepareStatement(deleteCartItemsQuery);
				deleteCartItemsStmt.setLong(1, userId);
				var deletedItems = deleteCartItemsStmt.executeUpdate();
				logger.debug("Number of items deleted from the cart "+deletedItems);
				// Commit the transaction
				logger.debug("Commiting transaction");
				con.commit();
				return true;
			} catch (SQLException e) {
				// Rollback in case of an error
				logger.error("Rollback transaction due to ", e);
				con.rollback();
				return false;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean deleteCart(Long uid, long pid) {
		var query = "delete from cart_items where buyer_id = ? and product_id = ?";
		try (Connection connection = dataSource.getConnection(); var st = connection.prepareStatement(query)) {
			st.setLong(1, uid);
			st.setLong(2, pid);
			return st.executeUpdate() > 0;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	@Override
	public void updateProductQuantity(Long uid, long pid, int quantity) {
		var query = "update cart_items set quantity = ? where buyer_id = ? and product_id = ?";
		try (Connection connection = dataSource.getConnection(); var st = connection.prepareStatement(query)) {
			st.setInt(1, quantity);
			st.setLong(2, uid);
			st.setLong(3, pid);
			st.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
