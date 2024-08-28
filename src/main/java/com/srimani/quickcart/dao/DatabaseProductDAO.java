package com.srimani.quickcart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.util.DataSource;

public class DatabaseProductDAO implements ProductDao {
	private final DataSource source;

	public DatabaseProductDAO(DataSource source) {
		this.source = source;
	}

	@Override
	public int addProduct(Product product) throws Exception {
		try (Connection connection = source.getConnection()) {
			PreparedStatement statement = connection
					.prepareStatement("insert into products (name, description, category, price) values(?,?,?,?)");
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setString(3, product.getCategory());
			statement.setDouble(4, product.getPrice());

			return statement.executeUpdate();
		}
	}

	@Override
	public Optional<Product> getProduct(long id) {
		try (Connection connection = source.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("select id, name, description, category, price from products where id=" + id);
			if (rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong(1));
				product.setName(rs.getString(2));
				product.setDescription(rs.getString(3));
				product.setCategory(rs.getString(4));
				product.setPrice(rs.getLong(5));
				return Optional.of(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public List<Product> findProducts(String query) {
		try (Connection connection = source.getConnection()) {
			var statement = connection.prepareStatement(
					"select id, name, description, category, price from products WHERE LOWER(name) LIKE ?");
			var q = query == null ? "" : query.toLowerCase();
			String sanitizedQuery = "%" + q + "%";
			statement.setString(1, sanitizedQuery);
			var results = statement.executeQuery();
			ArrayList<Product> products = new ArrayList<>();
			while (results.next()) {
				Product p = new Product();
				p.setId(results.getLong(1));
				p.setName(results.getString(2));
				p.setDescription(results.getString(3));
				p.setCategory(results.getString(4));
				p.setPrice(results.getDouble(5));

				products.add(p);
			}

			return products;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return List.of();
	}

	@Override
	public List<Product> findProductsByCategory(String category) {
		return null;
	}

	@Override
	public boolean deleteProduct(long id) {
		return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		return false;
	}
}
