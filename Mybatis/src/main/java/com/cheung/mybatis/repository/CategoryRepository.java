package com.cheung.mybatis.repository;

import java.util.List;

import com.cheung.mybatis.model.Category;

public interface CategoryRepository {
	public List<Category> findAll();

	public String categoryName(Integer categoryId);

	public List<Category> level1();

	public List<Category> level2(Integer parentId);

	public List<Category> level3(Integer parentId);

	public Category showFullPath(Integer categoryId);

	public Category findById(Integer categoryId);

	public int save(Category category);

	public int update(Category categroy);

	public int deleteById(Integer categoryId);
	
	public int deleteByParentId(Integer categoryId);
}
