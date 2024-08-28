package com.srimani.quickcart.dao;

import java.util.List;
import java.util.Optional;

import com.srimani.quickcart.entity.Product;

public interface ProductDao {
	int addProduct(Product product) throws Exception;

	Optional<Product> getProduct(long id);

	List<Product> findProducts(String name);

	List<Product> findProductsByCategory(String category);

	boolean deleteProduct(long id);

	boolean updateProduct(Product product);
}
