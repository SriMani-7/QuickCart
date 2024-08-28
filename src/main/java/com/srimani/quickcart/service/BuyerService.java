package com.srimani.quickcart.service;

import java.util.List;
import java.util.Optional;

import com.srimani.quickcart.entity.Product;

public interface BuyerService {

	List<Product> getProducts(String reqParameter);

	Optional<Product> getProduct(long pId);

}
