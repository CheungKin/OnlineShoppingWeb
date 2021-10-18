package com.cheung.mybatis.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Order {
	private int orderId;
	private int userId;
	private int total;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date delivery;
	private String status;
	private int productId;
}
