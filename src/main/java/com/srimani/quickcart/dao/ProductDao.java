package com.srimani.quickcart.dao;

import java.util.List;
import java.util.Optional;

import com.srimani.quickcart.dto.ProductManagementDTO;
import com.srimani.quickcart.entity.Product;

public interface ProductDao {
	boolean addProduct(Long id, Product product);

	Optional<Product> getProduct(long id);

	List<Product> findProducts(String name);

	List<Product> findProductsByCategory(String category);

	boolean deleteProduct(long id);

	boolean updateProduct(Long id, Product product);

	List<Product> getProductsByRetailer(Long id);

	Product getProduct(Long sellerId, long pId);

	void deleteProduct(Long sellerId, long productId);

	List<ProductManagementDTO> getAllProductsForAdmin();
}
