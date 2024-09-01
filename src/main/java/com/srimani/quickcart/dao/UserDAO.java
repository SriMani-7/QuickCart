package com.srimani.quickcart.dao;

import java.util.List;

import com.srimani.quickcart.dto.UserDTO;
import com.srimani.quickcart.entity.User;
import com.srimani.quickcart.exception.UserNotExistsException;

public interface UserDAO {

	boolean hasUsername(String username);

	long createUser(User user);

	User getUserByUsername(String username) throws UserNotExistsException;

	List<UserDTO> getAllUsers();

	void deleteUser(long userId) throws UserNotExistsException;

	void manageUser(long userId, String status) throws UserNotExistsException;

}
