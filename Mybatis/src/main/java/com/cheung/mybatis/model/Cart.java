package com.cheung.mybatis.model;

import lombok.Data;

@Data
public class Cart {
	private int cartId;
	private int userId;
	private int productId;
	private int quantity;
	private int cost;
	private Product product;
	private int total;
	private int count;
}
