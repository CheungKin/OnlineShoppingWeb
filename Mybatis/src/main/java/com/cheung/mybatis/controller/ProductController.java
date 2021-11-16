package com.cheung.mybatis.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.cheung.mybatis.model.Category;
import com.cheung.mybatis.model.Comment;
import com.cheung.mybatis.model.Product;
import com.cheung.mybatis.repository.CategoryRepository;
import com.cheung.mybatis.repository.CommentRepository;
import com.cheung.mybatis.repository.OrderRepository;
import com.cheung.mybatis.repository.ProductRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	protected CategoryRepository categoryRepository;

	public void listCategory(ModelMap map) {
		List<Category> categories = categoryRepository.findAll();
		map.addAttribute("categories", categories);
	}
	
	@GetMapping("/{id}")
	public String findById(@PathVariable("id") Integer productId, HttpSession session, ModelMap map) {
		Product product = productRepository.findById(productId);
		List<Comment> comments = commentRepository.findByProductId(productId);
		map.addAttribute("product", product);
		map.addAttribute("comments", comments);
		Integer userId = (Integer) session.getAttribute("userId");
		boolean showComment = (orderRepository.findByBothId(userId, productId).isEmpty()) ? false : true;
		map.addAttribute("showComment", showComment);
		this.listCategory(map);
		return "productDetail";
	}

	@PostMapping("/addComment")
	public String addComment(@RequestParam("productId") int productId, @RequestParam("userComment") String userComment,
			HttpSession session, ModelMap map) {
		Comment comment = new Comment();
		comment.setUserId((Integer) session.getAttribute("userId"));
		comment.setProductId(productId);
		comment.setUserComment(userComment);
		commentRepository.save(comment);
		return "redirect:/products/" + productId;
	}

	@GetMapping("/deleteComment/{id}")
	public String deleteComment(@PathVariable("id") Integer commentId) {
		Comment comment = commentRepository.findByCommentId(commentId);
		int productId = comment.getProductId();
		commentRepository.deleteById(commentId);
		return "redirect:/products/" + productId;
	}

	@GetMapping("/{level}/{categoryId}")
	public String category(@PathVariable("level") Integer level, @PathVariable("categoryId") Integer categoryId,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo, ModelMap map) {
		this.listCategory(map);
		String categoryName = categoryRepository.categoryName(categoryId);
		map.addAttribute("name", categoryName);
		PageHelper.startPage(pageNo, 8);
		List<Product> products = null;
		if (level == 2) {
			products = productRepository.findBylevel2(categoryId);
		} else {
			products = productRepository.findBylevel3(categoryId);
		}
		PageInfo<Product> page = new PageInfo<Product>(products);
		map.addAttribute("products", products);
		map.addAttribute("pageNo", pageNo);
		map.addAttribute("page", page);
		return "showProducts";
	}

	@GetMapping("/search")
	public String search(@RequestParam("name") String productName,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo, ModelMap map) {
		this.listCategory(map);
		String search = "%" + productName + "%";
		PageHelper.startPage(pageNo, 8);
		List<Product> products = productRepository.search(search);
		PageInfo<Product> page = new PageInfo<Product>(products);
		map.addAttribute("name", productName);
		map.addAttribute("products", products);
		map.addAttribute("pageNo", pageNo);
		map.addAttribute("page", page);
		return "showProducts";
	}
}
