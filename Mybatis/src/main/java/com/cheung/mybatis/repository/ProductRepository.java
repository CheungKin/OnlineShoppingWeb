package com.cheung.mybatis.repository;

import java.util.List;

import com.cheung.mybatis.model.Product;

public interface ProductRepository {

	public List<Product> findAll();

	public Product findById(Integer productId);

	public int save(Product product);

	public int update(Product product);

	public int deleteById(Integer productId);

	public List<Product> findBylevel2(Integer categoryId);

	public List<Product> findBylevel3(Integer categoryId);
	
	public List<Product> search(String productName);
}
