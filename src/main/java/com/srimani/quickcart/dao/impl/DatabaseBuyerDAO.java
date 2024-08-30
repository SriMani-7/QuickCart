package com.srimani.quickcart.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.srimani.quickcart.dao.BuyerDAO;
import com.srimani.quickcart.entity.Buyer;
import com.srimani.quickcart.util.DataSource;

public class DatabaseBuyerDAO implements BuyerDAO {

	private DataSource dataSource;

	public DatabaseBuyerDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void createBuyer(Buyer buyer) {
		try (var con = dataSource.getConnection()) {
			String query = "INSERT INTO buyers (user_id, city, pincode, phone_number) VALUES (?, ?, ?, ?)";
			var stmt = con.prepareStatement(query);
			stmt.setLong(1, buyer.getUserId());
			stmt.setString(2, buyer.getCity());
			stmt.setInt(3, buyer.getPincode());
			stmt.setString(4, buyer.getPhoneNumber());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Buyer getBuyerByUserId(long userId) {
		try (var con = dataSource.getConnection()) {
			String query = "SELECT * FROM buyers WHERE user_id = ?";
			var stmt = con.prepareStatement(query);
			stmt.setLong(1, userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Buyer b = new Buyer();
				b.setId(rs.getLong("id"));
				b.setUserId(rs.getLong("user_id"));
				b.setPincode(rs.getInt("pincode"));
				b.setCity(rs.getString("city"));
				b.setPhoneNumber(rs.getString("phone_number"));
				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
