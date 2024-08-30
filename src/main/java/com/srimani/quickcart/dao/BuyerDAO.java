package com.srimani.quickcart.dao;

import com.srimani.quickcart.entity.Buyer;

public interface BuyerDAO {
	void createBuyer(Buyer buyer);

	Buyer getBuyerByUserId(long userId);
}
