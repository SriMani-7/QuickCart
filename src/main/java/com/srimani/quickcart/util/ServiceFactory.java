package com.srimani.quickcart.util;

import com.srimani.quickcart.service.AdminService;
import com.srimani.quickcart.service.AdminServiceImpl;
import com.srimani.quickcart.service.AuthenticationService;
import com.srimani.quickcart.service.AuthenticationServiceImpl;
import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.service.BuyerServiceImpl;
import com.srimani.quickcart.service.CartService;
import com.srimani.quickcart.service.CartServiceImpl;
import com.srimani.quickcart.service.SellerService;
import com.srimani.quickcart.service.SellerServiceImpl;

public class ServiceFactory {

	private ServiceFactory() {

	}

	public static BuyerService getBuyerService() {
		return new BuyerServiceImpl(DAOFactory.getInstance());
	}

	public static AuthenticationService getAuthenticationService() {
		return new AuthenticationServiceImpl(DAOFactory.getInstance());
	}

	public static SellerService getSellerService() {
		return new SellerServiceImpl(DAOFactory.getInstance());
	}

	public static AdminService getAdminService() {
		return new AdminServiceImpl(DAOFactory.getInstance());
	}

	public static CartService getCartService() {
		return new CartServiceImpl(DAOFactory.getInstance());
	}
}
