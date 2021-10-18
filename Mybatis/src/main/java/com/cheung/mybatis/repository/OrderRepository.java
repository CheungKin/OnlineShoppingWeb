package com.cheung.mybatis.repository;

import java.util.List;

import com.cheung.mybatis.model.Order;

public interface OrderRepository {
	
	public List<Order> findAll();
	
	public List<Order> findByuserId(Integer userId);
	
	public List<Order> findByBothId(Integer userId, Integer productId);

	public int save(Order order);

	public int update(Order order);

	public int delete(Integer orderId);
}
