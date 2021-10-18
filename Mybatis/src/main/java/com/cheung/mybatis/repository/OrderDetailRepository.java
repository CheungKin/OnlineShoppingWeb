package com.cheung.mybatis.repository;

import java.util.List;

import com.cheung.mybatis.model.OrderDetail;

public interface OrderDetailRepository {
	
	public List<OrderDetail> findByorderId(Integer orderId);
	
	public int save(OrderDetail orderDetail);
	
	public int delete(Integer orderId);
}
