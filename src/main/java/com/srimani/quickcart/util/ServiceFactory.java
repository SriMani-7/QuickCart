package com.srimani.quickcart.util;

import com.srimani.quickcart.service.AdminService;
import com.srimani.quickcart.service.AdminServiceImpl;
import com.srimani.quickcart.service.AuthService;
import com.srimani.quickcart.service.AuthServiceImpl;
import com.srimani.quickcart.service.BuyerService;
import com.srimani.quickcart.service.BuyerServiceImpl;
import com.srimani.quickcart.service.SellerService;
import com.srimani.quickcart.service.SellerServiceImpl;

public class ServiceFactory {

	private ServiceFactory() {

	}

	public static BuyerService getBuyerService() {
		return new BuyerServiceImpl(DAOFactory.getInstance());
	}

	public static AuthService getAuthenticationService() {
		return new AuthServiceImpl(DAOFactory.getInstance());
	}

	public static SellerService getSellerService() {
		return new SellerServiceImpl(DAOFactory.getInstance());
	}

	public static AdminService getAdminService() {
		return new AdminServiceImpl(DAOFactory.getInstance());
	}

}
