package com.srimani.quickcart.dao;

import java.util.List;
import java.util.Optional;

import com.srimani.quickcart.dto.ProductManagementDTO;
import com.srimani.quickcart.entity.Product;
import com.srimani.quickcart.entity.Retailer;
import com.srimani.quickcart.exception.ProductNotFoundException;

public interface ProductDao {
	boolean addProduct(Long retailerId, Product product);

	Optional<Product> getProduct(long id);

	List<Product> findProducts(String name, String category);

	boolean updateProduct(Long retailerId, Product product);

	List<Product> getProductsByRetailer(Long id);

	Product getProduct(Long retailerId, long pId) throws ProductNotFoundException;

	void deleteProduct(Long retailerId, long productId) throws ProductNotFoundException;

	List<ProductManagementDTO> getAllProductsForAdmin();

	List<String> getCategories();

	Retailer getProductRetailer(long id);
}
