package com.srimani.quickcart.util;

import com.srimani.quickcart.service.*;

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
