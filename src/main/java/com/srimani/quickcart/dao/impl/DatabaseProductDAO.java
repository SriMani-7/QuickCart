package com.srimani.quickcart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.srimani.quickcart.dao.ProductDao;
import com.srimani.quickcart.dto.ProductManagementDTO;
import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.util.DataSource;

public class DatabaseProductDAO implements ProductDao {
	private final DataSource source;

	public DatabaseProductDAO(DataSource source) {
		this.source = source;
	}

	@Override
	public boolean addProduct(Long sellerId, Product product) {
		try (Connection connection = source.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"insert into products (name, description, category, price, retailer_id, image_url) values(?,?,?,?,?,?)");
			statement.setString(1, product.getName());
			statement.setString(2, product.getDescription());
			statement.setString(3, product.getCategory());
			statement.setDouble(4, product.getPrice());
			statement.setLong(5, sellerId);
			statement.setString(6, product.getImageUrl());

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Optional<Product> getProduct(long id) {
		try (Connection connection = source.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(
					"select id, name, description, category, price, image_url from products where id=" + id);
			if (rs.next()) {
				return Optional.of(fromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public List<Product> findProducts(String query, String category) {
		try (Connection connection = source.getConnection()) {
			var catePro = "select id, name, description, category, price, image_url from products WHERE LOWER(name) LIKE ? and category = ?";
			var allCat = "select id, name, description, category, price, image_url from products WHERE LOWER(name) LIKE ?";
			var statement = connection
					.prepareStatement(category == null ? allCat : category.isEmpty() ? allCat : catePro);
			var q = query == null ? "" : query.toLowerCase();
			String sanitizedQuery = "%" + q + "%";
			statement.setString(1, sanitizedQuery);
			if (category != null && !category.isEmpty())
				statement.setString(2, category);
			var results = statement.executeQuery();
			ArrayList<Product> products = new ArrayList<>();
			while (results.next()) {
				products.add(fromResultSet(results));
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
	public List<Product> getProductsByRetailer(Long id) {
		try (Connection connection = source.getConnection()) {
			var statement = connection.prepareStatement(
					"select id, name, description, category, price, image_url from products WHERE retailer_id = ?");
			statement.setLong(1, id);

			var results = statement.executeQuery();
			ArrayList<Product> products = new ArrayList<>();
			while (results.next()) {
				products.add(fromResultSet(results));
			}

			return products;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return List.of();
	}

	@Override
	public Product getProduct(Long sellerId, long pId) {
		try (Connection connection = source.getConnection()) {
			var statement = connection.prepareStatement(
					"select id, name, description, category, price, image_url from products where id=? and retailer_id=?");
			statement.setLong(1, pId);
			statement.setLong(2, sellerId);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return fromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateProduct(Long id, Product product) {
		try (var con = source.getConnection()) {
			var st = con.prepareStatement(
					"update products set name = ?, description = ?, category = ?, price = ?, image_url = ? where retailer_id = ? and id = ?");
			st.setString(1, product.getName());
			st.setString(2, product.getDescription());
			st.setString(3, product.getCategory());
			st.setDouble(4, product.getPrice());
			st.setString(5, product.getImageUrl());
			st.setLong(6, id);
			st.setLong(7, product.getId());

			return st.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void deleteProduct(Long sellerId, long productId) {
		try (var con = source.getConnection()) {
			var st = con.prepareStatement("delete from products where id = ? and retailer_id = ?");
			st.setLong(1, productId);
			st.setLong(2, sellerId);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<ProductManagementDTO> getAllProductsForAdmin() {
		List<ProductManagementDTO> products = new ArrayList<>();

		String sql = """
				SELECT p.id,
				       p.name,
				       p.image_url,
				       p.category,
				       p.price,
				       u.username AS sellerUsername,
				       COALESCE(oi.totalOrders, 0) AS totalOrders
				FROM products p
				left JOIN retailers r ON p.retailer_id = r.user_id
				left JOIN users u ON r.user_id = u.id
				LEFT JOIN (
				    SELECT oi.product_id, COUNT(*) AS totalOrders
				    FROM order_items oi
				    GROUP BY oi.product_id
				) oi ON p.id = oi.product_id;
								""";

		try (Connection con = source.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				ProductManagementDTO product = new ProductManagementDTO();
				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getDouble("price"));
				product.setSellerUsername(rs.getString("sellerUsername"));
				product.setTotalOrders(rs.getInt("totalOrders"));
				product.setImageUrl(rs.getString("image_url"));

				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	@Override
	public List<String> getCategories() {
		return source.withQuery("select category from products", st -> {
			try {
				List<String> cList = new LinkedList<String>();
				var set = st.executeQuery();
				while (set.next())
					cList.add(set.getString(1));
				return cList;
			} catch (SQLException e) {
				e.printStackTrace();
				return List.of();
			}
		});
	}

	private Product fromResultSet(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getLong(1));
		product.setName(rs.getString(2));
		product.setDescription(rs.getString(3));
		product.setCategory(rs.getString(4));
		product.setPrice(rs.getLong(5));
		product.setImageUrl(rs.getString(6));

		return product;
	}

	@Override
	public Retailer getRetailer(long id) {
		var query = "select r.name, r.address from retailers r inner join products p on p.retailer_id = r.user_id where p.id = ?";
		return source.withQuery(query, st -> {
			st.setLong(1, id);
			var set = st.executeQuery();
			var r = new Retailer();
			if (set.next()) {
				r.setName(set.getString(1));
				r.setAddress(set.getString(2));
			}
			return r;
		});
	}

}
