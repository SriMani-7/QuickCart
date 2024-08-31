package com.srimani.quickcart.dao;

import com.srimani.quickcart.entity.Retailer;

public interface RetailerDAO {

	void createRetailer(Retailer s);

	Retailer getProfile(Long uid);

	void updateProfile(Retailer retailer);

}
