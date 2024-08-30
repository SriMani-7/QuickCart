package com.srimani.quickcart.service;

import java.util.List;

import com.srimani.quickcart.dto.ProductManagementDTO;
import com.srimani.quickcart.dto.UserDTO;

public interface AdminService {

	List<UserDTO> getAllUsers();

	void blockUser(long userId);

	void deleteUser(long userId);

	void activateUser(long userId);

	List<ProductManagementDTO> getAllProducts();

}
