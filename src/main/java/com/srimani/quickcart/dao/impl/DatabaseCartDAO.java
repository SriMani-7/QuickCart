package com.srimani.quickcart.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.srimani.quickcart.dao.ShappingCartDAO;
import com.srimani.quickcart.dto.CartItem;
import com.srimani.quickcart.util.DataSource;

public class DatabaseCartDAO implements ShappingCartDAO {

	private DataSource dataSource;

	public DatabaseCartDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int addProduct(long userId, long productId) {
		try (var con = dataSource.getConnection()) {
			var st = con.prepareStatement("insert into cart_items (buyer_id,product_id, quantity) values(?,?,1)");
			st.setLong(1, userId);
			st.setLong(2, productId);
			return st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public LinkedList<CartItem> getCartItemsForUserId(long userId) {
		try (var con = dataSource.getConnection()) {
			var query = "select p.name, p.price, p.description, ci.quantity, p.id  from products p inner join cart_items ci on ci.product_id = p.id where ci.buyer_id = ?";
			var st = con.prepareStatement(query);
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
			// TODO: handle exception
			e.printStackTrace();
		}
		return new LinkedList<CartItem>();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkoutCart(Long userId, String city, String address, String pincode, String phoneNumber,
			String paymentMethod) {
		try (var con = dataSource.getConnection()) {
			con.setAutoCommit(false); // Begin transaction

			try {
				// Calculate the total price
				List<CartItem> items = getCartItemsForUserId(userId);
				double totalPrice = items.stream().mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getPrice())
						.sum();

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

				// Insert into order_items table
				String orderItemsQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
				PreparedStatement orderItemsStmt = con.prepareStatement(orderItemsQuery);

				for (CartItem item : items) {
					orderItemsStmt.setLong(1, orderId);
					orderItemsStmt.setLong(2, item.getProductId());
					orderItemsStmt.setInt(3, item.getQuantity());
					orderItemsStmt.setDouble(4, item.getPrice());
					orderItemsStmt.addBatch(); // Add to batch for efficiency
				}

				orderItemsStmt.executeBatch();

				// Delete the items from the cart after checkout
				String deleteCartItemsQuery = "DELETE FROM cart_items WHERE buyer_id = ?";
				PreparedStatement deleteCartItemsStmt = con.prepareStatement(deleteCartItemsQuery);
				deleteCartItemsStmt.setLong(1, userId);
				deleteCartItemsStmt.executeUpdate();

				// Commit the transaction
				con.commit();
				return true;
			} catch (SQLException e) {
				// Rollback in case of an error
				con.rollback();
				e.printStackTrace();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
