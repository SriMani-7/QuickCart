package com.srimani.quickcart.service;

import com.srimani.quickcart.entity.Buyer;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.entity.User;

public interface AuthenticationService {

	boolean hasUsername(String username);

	void createBuyer(Buyer buyer);

	long createUser(User user);

	User authenticate(String username, String password);

	void createRetailer(Retailer s);

}
