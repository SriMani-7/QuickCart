package com.srimani.quickcart.dao;

import java.util.LinkedList;

import com.srimani.quickcart.dto.CartItem;

public interface ShappingCartDAO {

	int addProduct(long userId, long productId);

	LinkedList<CartItem> getCartItemsForUserId(long userId);

	boolean isProductInCart(Long userId, long id);

	boolean checkoutCart(Long userId, String city, String address, String pincode, String phoneNumber,
			String paymentMethod);

}
