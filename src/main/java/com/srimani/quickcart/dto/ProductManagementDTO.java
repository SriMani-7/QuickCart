package com.srimani.quickcart.dto;

public class ProductManagementDTO {
	private long id;
	private String name;
	private String category;
	private double price;
	private String sellerUsername;
	private int totalOrders;
	private String imageUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	public int getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}

	public void setImageUrl(String string) {
		this.imageUrl = string;
	}

	public String getImageUrl() {
		return imageUrl;
	}

}
