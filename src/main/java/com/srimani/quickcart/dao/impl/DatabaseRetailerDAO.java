package com.srimani.quickcart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.srimani.quickcart.dao.RetailerDAO;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.util.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseRetailerDAO implements RetailerDAO {
	private DataSource dataSource;
	private final Logger logger = LogManager.getLogger();

	public DatabaseRetailerDAO(DataSource dataSource) {
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
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public Retailer getProfile(Long uid) {
		return dataSource.withQuery("select * from retailers where user_id = ?", st -> {
			var re = new Retailer();
			st.setLong(1, uid);
			var results = st.executeQuery();
			if (results.next()) {
				re.setUserId(results.getLong("user_id"));
				re.setName(results.getString("name"));
				re.setContactEMail(results.getString("contact_email"));
				re.setAddress(results.getString("address"));
				re.setPhoneNumber(results.getString("phone_number"));
			}
			return re;
		});
	}

	@Override
	public void updateProfile(Retailer retailer) {
		var query = "UPDATE retailers SET name = ?, contact_email = ?, address = ?, phone_number = ? WHERE user_id = ?";
		dataSource.withQuery(query, st -> {
			st.setString(1, retailer.getName());
			st.setString(2, retailer.getContactEMail());
			st.setString(3, retailer.getAddress());
			st.setString(4, retailer.getPhoneNumber());
			st.setLong(5, retailer.getUserId());

			st.executeUpdate(); // Execute the update query

			return null;
		});
	}

}
