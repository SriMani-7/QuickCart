package com.srimani.quickcart.dao;

import java.util.List;

import com.srimani.quickcart.dto.ProductReview;
import com.srimani.quickcart.entity.Review;

public interface ReviewDAO {

	int postReview(Review review);

	List<ProductReview> getProductReviews(long id);

	List<Review> getBuyerProductReviews(Long uid);

	void deleteReview(long pid, long userId);

}
