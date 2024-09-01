package com.srimani.quickcart.exception;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(long id) {
        super("Product not found id: " + id);
    }

    public ProductNotFoundException(String name) {
        super("Product not found on given name " + name);
    }
}
