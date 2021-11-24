package com.cheung.mybatis.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cheung.mybatis.model.Category;
import com.cheung.mybatis.model.Order;
import com.cheung.mybatis.model.Product;
import com.cheung.mybatis.model.User;
import com.cheung.mybatis.repository.CategoryRepository;
import com.cheung.mybatis.repository.OrderDetailRepository;
import com.cheung.mybatis.repository.OrderRepository;
import com.cheung.mybatis.repository.ProductRepository;
import com.cheung.mybatis.repository.UserRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@GetMapping("/user")
	public String findAllUser(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo, ModelMap map) {
		PageHelper.startPage(pageNo, 8);
		List<User> users = userRepository.findAll();
		PageInfo<User> page = new PageInfo<User>(users);
		map.addAttribute("users", users);
		map.addAttribute("pageNo", pageNo);
		map.addAttribute("page", page);
		return "ListUsers";
	}

	@GetMapping("/user/{userId}")
	public String findByUserId(@PathVariable("userId") Integer userID, ModelMap map) {
		User user = userRepository.findById(userID);
		if (user == null) {
			map.addAttribute("isEmpty", true);
		} else {
			map.addAttribute("isEmpty", false);
			map.addAttribute("user", user);
		}
		return "showUser";
	}

	@GetMapping("/user/delete/{userId}")
	public String deleteById(@PathVariable("userId") Integer userId) {
		userRepository.deleteById(userId);
		return "redirect:/admin/user/";
	}

	@GetMapping("/user/search")
	public String search(@RequestParam("name") String name,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo, ModelMap map) {
		String search = "%" + name + "%";
		PageHelper.startPage(pageNo, 8);
		List<User> users = userRepository.search(search);
		PageInfo<User> page = new PageInfo<User>(users);
		map.addAttribute("users", users);
		map.addAttribute("pageNo", pageNo);
		map.addAttribute("page", page);
		return "ListUsers";
	}

	@GetMapping("/product/create")
	public String createProduct(ModelMap map) {
		map.addAttribute("product", new Product());
		List<Category> categories = categoryRepository.findAll();
		map.addAttribute("categories", categories);
		return "createProduct";
	}

	@PostMapping("/product/create")
	public String createProduct(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile image,
			@RequestParam("categoryId") int categoryId, ModelMap map) throws IllegalStateException, IOException {
		product.setCategoryId(categoryId);
		try {
			String path = StringUtils.cleanPath(image.getOriginalFilename());
			product.setPhoto("/images/" + path);
			String filePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
			File filepath = new File(filePath + path);
			image.transferTo(filepath);
		} catch (Exception e) {
			product.setPhoto("/images/NOPhoto.jpeg");
		}
		productRepository.save(product);
		int productId = product.getProductId();
		return "redirect:/products/" + productId;
	}

	@GetMapping("/product/update/{productId}")
	public String updateProduct(@PathVariable("productId") Integer productId, ModelMap map) {
		Product product = productRepository.findById(productId);
		Category category = categoryRepository.showFullPath(product.getCategoryId());
		map.addAttribute("product", product);
		map.addAttribute("category", category);
		return "updateProduct";
	}

	@PostMapping("/product/update/{productId}")
	public String updateProduct(@PathVariable("productId") int productId, @ModelAttribute("product") Product product,
			@RequestParam("image") MultipartFile image, ModelMap map) throws IllegalStateException, IOException {
		if (image.isEmpty()) {
			productRepository.update(product);
		} else {
			String path = StringUtils.cleanPath(image.getOriginalFilename());
			product.setPhoto("/images/" + path);
			String filePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
			File filepath = new File(filePath + path);
			image.transferTo(filepath);
			productRepository.update(product);
		}
		return "redirect:/products/" + productId;
	}

	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") Integer productId) {
		productRepository.deleteById(productId);
		return "redirect:/";
	}

	@PostMapping("/order/update")
	public String update(@RequestParam("orderId") Integer orderId, @RequestParam("status") String status) {
		Order order = new Order();
		order.setOrderId(orderId);
		order.setStatus(status);
		orderRepository.update(order);
		return "redirect:/order/";
	}

	@GetMapping("/order/delete/{orderId}")
	public String delete(@PathVariable("orderId") Integer orderId) {
		orderDetailRepository.delete(orderId);
		orderRepository.delete(orderId);
		return "redirect:/order/";
	}

}
