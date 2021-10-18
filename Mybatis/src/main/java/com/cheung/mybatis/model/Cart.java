package com.cheung.mybatis.model;

import lombok.Data;

@Data
public class Cart {
	private int cartId;
	private int userId;
	private int productId;
	private int quantity;
	private int cost;
	private String productName;
	private String photo;
	private int total;
	private int stock;
	private int count;
}
