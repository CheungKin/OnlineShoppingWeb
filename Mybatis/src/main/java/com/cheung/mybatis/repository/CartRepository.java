package com.cheung.mybatis.repository;

import java.util.List;

import com.cheung.mybatis.model.Cart;

public interface CartRepository {
	public Cart unique(Integer userId, Integer productId);

	public List<Cart> findByuserId(Integer userId);

	public Cart findBycartId(Integer cartId);

	public int save(Cart cart);

	public int update(Cart cart);

	public int deleteBycartId(Integer cartId);
	
	public int total(Integer userId);

	public int deleteByuserId(Integer userId);
	
	public int count(Integer userId);
}
