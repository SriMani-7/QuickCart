package com.srimani.quickcart.service;

import java.util.List;

import com.srimani.quickcart.dto.ProductManagementDTO;
import com.srimani.quickcart.dto.UserDTO;
import com.srimani.quickcart.exception.UserNotExistsException;

public interface AdminService {

	List<UserDTO> getAllUsers();

	void blockUser(long userId) throws UserNotExistsException;

	void deleteUser(long userId) throws UserNotExistsException;

	void activateUser(long userId) throws UserNotExistsException;

	List<ProductManagementDTO> getAllProducts();

}
