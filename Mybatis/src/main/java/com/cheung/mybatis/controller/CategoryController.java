package com.cheung.mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cheung.mybatis.model.Category;
import com.cheung.mybatis.repository.CategoryRepository;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	public void listCategory(ModelMap map) {
		List<Category> categories = categoryRepository.findAll();
		map.addAttribute("categories", categories);
	}

	@GetMapping("/")
	public String categoryList(ModelMap map) {
		this.listCategory(map);
		return "category";
	}

	@GetMapping("/add")
	public String addCategory(ModelMap map) {
		map.addAttribute("categories", this.allLevel1());
		return "addCategory";
	}

	@PostMapping("/add")
	public String addNewCategory(@RequestParam("categoryId1") int categoryId1,
			@RequestParam("categoryId2") int categoryId2, @RequestParam("categoryName") String categoryName,
			ModelMap map) {
		Category category = new Category();
		if (categoryId1 == 0 && categoryId2 == 0) {
			category.setLevel(1);
		} else if (categoryId2 == 0) {
			category.setLevel(2);
			category.setParentId(categoryId1);
		} else {
			category.setLevel(3);
			category.setParentId(categoryId2);
		}
		category.setCategoryName(categoryName);
		categoryRepository.save(category);
		return "redirect:/category/";
	}

	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") int categoryId) {
		categoryRepository.delete(categoryId);
		return "redirect:/category/";
	}

	@GetMapping("/update/{categoryId}")
	public String updateCategory(@PathVariable("categoryId") int categoryId, ModelMap map) {
		Category category = categoryRepository.findById(categoryId);
		int level = category.getLevel();
		map.addAttribute("level", level);
		switch (level) {
		case 1:
			map.addAttribute("category", category);
			break;
		case 2:
		case 3:
			map.addAttribute("category", category);
			map.addAttribute("categories", categoryRepository.level1());
			break;
		}
		return "updateCategory";
	}

	@PostMapping("/update/{categoryId}")
	public String updateCategory(@PathVariable("categoryId") int categoryId,
			@RequestParam("categoryName") String categoryName,
			@RequestParam(value = "level1Id", defaultValue = "0") int level1Id,
			@RequestParam(value = "level2Id", defaultValue = "0") int level2Id) {
		Category category = new Category();
		category.setCategoryId(categoryId);
		category.setCategoryName(categoryName);
		int parentId;
		if (level2Id == 0) {
			parentId = level1Id;
		} else if (level1Id == 0) {
			parentId = level2Id;
		} else {
			parentId = 0;
		}
		category.setParentId(parentId);
		categoryRepository.update(category);
		return "redirect:/category/";
	}

	@ResponseBody
	@RequestMapping("/level1")
	public List<Category> allLevel1() {
		return categoryRepository.level1();
	}

	@ResponseBody
	@RequestMapping("/level2")
	public List<Category> allLevel2(String categoryId1) {
		Integer parentId = Integer.parseInt(categoryId1);
		return categoryRepository.level2(parentId);
	}

	@ResponseBody
	@RequestMapping("/level3")
	public List<Category> allLevel3(String categoryId2) {
		Integer parentId = Integer.parseInt(categoryId2);
		return categoryRepository.level3(parentId);
	}
}
