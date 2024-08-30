package com.srimani.quickcart.dao;

import java.util.List;

import com.srimani.quickcart.dto.UserDTO;
import com.srimani.quickcart.entity.User;

public interface UserDAO {

	boolean hasUsername(String username);

	long createUser(User user);

	User getUserById(long id);

	User getUserByUsername(String username);

	List<UserDTO> getAllUsers();

	void manageUser(long userId, String string);

}
