package com.srimani.quickcart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.srimani.quickcart.dao.RetailerDAO;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.util.DataSource;

public class DatabaseRetailerDAO implements RetailerDAO {
	private DataSource dataSource;

	public DatabaseRetailerDAO(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.dataSource = dataSource;
	}

	@Override
	public void createRetailer(Retailer retailer) {
		String sql = "INSERT INTO retailers (user_id, name, contact_email, phone_number, address) VALUES (?, ?, ?, ?, ?)";

		try (Connection con = dataSource.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setLong(1, retailer.getUserId());
			pstmt.setString(2, retailer.getName());
			pstmt.setString(3, retailer.getContactEMail());
			pstmt.setString(4, retailer.getPhoneNumber());
			pstmt.setString(5, retailer.getAddress());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace(); // You may want to handle this more gracefully
		}
	}

}
