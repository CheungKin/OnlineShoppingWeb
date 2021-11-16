package com.cheung.mybatis.model;


import lombok.Data;

@Data
public class Product {
	private int productId;
	private String productName;
	private int categoryId;
	private int stock;
	private int price;
	private String photo;
	private String description;
	private String categoryName;
}
