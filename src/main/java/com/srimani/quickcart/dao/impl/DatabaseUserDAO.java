package com.srimani.quickcart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.srimani.quickcart.dao.UserDAO;
import com.srimani.quickcart.dto.UserDTO;
import com.srimani.quickcart.entity.User;
import com.srimani.quickcart.exception.UserNotExistsException;
import com.srimani.quickcart.util.DataSource;

public class DatabaseUserDAO implements UserDAO {

	private DataSource dataSource;

	public DatabaseUserDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public boolean hasUsername(String username) {
		try (Connection c = dataSource.getConnection()) {
			String query = "SELECT COUNT(*) FROM users WHERE username = ?";
			PreparedStatement stmt = c.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public long createUser(User user) {
		try (var con = dataSource.getConnection()) {
			String query = "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getRole());
			stmt.setString(4, user.getEmail());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getLong(1); // Return the generated user ID
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public User getUserByUsername(String username) throws UserNotExistsException {
		try (Connection con = dataSource.getConnection()) {
			String query = "SELECT * FROM users WHERE username = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				User u = new User(rs.getString("username"), rs.getString("password"), rs.getString("role"),
						rs.getString("email"));
				u.setId(rs.getLong("id"));
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new UserNotExistsException(username);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserDTO> users = new ArrayList<>();
		String sql = "SELECT id, username, email, role, created_at, status FROM users";

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				UserDTO user = new UserDTO();
				user.setId(rs.getLong(1));
				user.setUsername(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setUserType(rs.getString(4));
				user.setCreatedAt(rs.getTimestamp(5));
				user.setStatus(rs.getString(6));
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void deleteUser(long userId) throws UserNotExistsException {
		try(var con = dataSource.getConnection()) {
			var st = con.prepareStatement("DELETE FROM users WHERE id = ?");
			st.setLong(1, userId);
			var rows = st.executeUpdate();
			if (rows <= 0) throw new UserNotExistsException(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void manageUser(long userId, String status) throws UserNotExistsException {
		try(var con = dataSource.getConnection()) {
			var st = con.prepareStatement("UPDATE users SET status = ? WHERE id = ?");
			st.setString(1, status);
			st.setLong(2, userId);
			var rows = st.executeUpdate();
			if (rows <= 0) throw new UserNotExistsException(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
