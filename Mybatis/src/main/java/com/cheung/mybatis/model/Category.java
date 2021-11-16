package com.cheung.mybatis.model;

import java.util.List;

import lombok.Data;

@Data
public class Category {
	private int categoryId;
	private String categoryName;
	private int parentId;
	private int level;
	private List<Category> subCategory;
	private Category category;
}
