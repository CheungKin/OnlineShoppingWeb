package com.cheung.mybatis.model;

import lombok.Data;

@Data
public class OrderDetail {
	private int detailId;
	private int orderId;
	private int productId;
	private int quantity;
	private int cost;
	private Product product;

}
