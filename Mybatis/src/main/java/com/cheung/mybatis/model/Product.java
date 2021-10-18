package com.cheung.mybatis.model;


import lombok.Data;

@Data
public class Product {
	private int productId;
	private String productName;
	private String category;
	private int stock;
	private int price;
	private String photo;
	private String description;
}
