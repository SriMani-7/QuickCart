package com.srimani.quickcart.dao.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.srimani.quickcart.dao.ReviewDAO;
import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.entity.Review;
import com.srimani.quickcart.util.DataSource;

public class DatabaseReviewDAO implements ReviewDAO {

	private final DataSource source;

	public DatabaseReviewDAO(DataSource source) {
		super();
		this.source = source;
	}

	@Override
	public int postReview(Review review) {
		try (var con = source.getConnection()) {
			var stm = con
					.prepareStatement("insert into reviews (user_id, product_id, message, rating) values (?,?,?,?)");
			stm.setLong(1, review.getUserId());
			stm.setLong(2, review.getProductId());
			stm.setString(3, review.getMessage());
			stm.setDouble(4, review.getRating());

			return stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<ProductReview> getProductReviews(long id) {
		try (var con = source.getConnection()) {
			var query = "SELECT username, message, rating from users inner join reviews on users.id = reviews.user_id";
			var st = con.createStatement();
			var set = st.executeQuery(query);
			List<ProductReview> prsList = new LinkedList<ProductReview>();
			while (set.next()) {
				var p = new ProductReview();
				p.setUsername(set.getString(1));
				p.setMessage(set.getString(2));
				p.setRating(set.getDouble(3));
				prsList.add(p);
			}
			return prsList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return List.of();
	}

}
