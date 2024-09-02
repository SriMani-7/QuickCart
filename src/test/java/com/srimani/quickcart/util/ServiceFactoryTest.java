package com.srimani.quickcart.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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

public class ServiceFactoryTest {

	@Test
	public void testGetBuyerService() {
		BuyerService buyerService = ServiceFactory.getBuyerService();
		assertNotNull(buyerService);
		assertTrue(buyerService instanceof BuyerServiceImpl);
	}

	@Test
	public void testGetAuthenticationService() {
		AuthenticationService authService = ServiceFactory.getAuthenticationService();
		assertNotNull(authService);
		assertTrue(authService instanceof AuthenticationServiceImpl);
	}

	@Test
	public void testGetSellerService() {
		SellerService sellerService = ServiceFactory.getSellerService();
		assertNotNull(sellerService);
		assertTrue(sellerService instanceof SellerServiceImpl);
	}

	@Test
	public void testGetAdminService() {
		AdminService adminService = ServiceFactory.getAdminService();
		assertNotNull(adminService);
		assertTrue(adminService instanceof AdminServiceImpl);
	}

	@Test
	public void testGetCartService() {
		CartService cartService = ServiceFactory.getCartService();
		assertNotNull(cartService);
		assertTrue(cartService instanceof CartServiceImpl);
	}
}
