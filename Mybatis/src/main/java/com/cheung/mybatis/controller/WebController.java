package com.cheung.mybatis.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheung.mybatis.model.Address;
import com.cheung.mybatis.model.Category;
import com.cheung.mybatis.model.Product;
import com.cheung.mybatis.model.User;
import com.cheung.mybatis.repository.AddressRepository;
import com.cheung.mybatis.repository.CartRepository;
import com.cheung.mybatis.repository.CategoryRepository;
import com.cheung.mybatis.repository.ProductRepository;
import com.cheung.mybatis.repository.UserRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class WebController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping({"/","/index"})
	public String findAll(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo, ModelMap map,
			HttpSession session) {
		this.listCategory(map);
		PageHelper.startPage(pageNo, 8);
		List<Product> products = productRepository.findAll();
		PageInfo<Product> page = new PageInfo<Product>(products);
		map.addAttribute("products", products);
		map.addAttribute("pageNo", pageNo);
		map.addAttribute("page", page);
		return ("index");
	}
	
	@GetMapping("/register")
	public String create(ModelMap map) {
		map.addAttribute("user", new User());
		map.addAttribute("address", new Address());
		return "createUser";
	}
	
	@PostMapping("/register")
	public String save(@ModelAttribute("user") User user,@ModelAttribute("address") Address address, @RequestParam("role") String role, ModelMap model) {
		User resultName = userRepository.findByNameOrEmail(user.getUserName());
		User resultEmail = userRepository.findByNameOrEmail(user.getEmail());
		if (resultName == null && resultEmail == null) {
			BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
			user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
			user.setRole(role);
			userRepository.save(user);
			int userId = user.getUserId();
			address.setUserId(userId);
			addressRepository.save(address);
			return "redirect:/login";
		} else {
			model.addAttribute("err", "The user is existing!");
			return "createUser";
		}
	}
	
	@GetMapping("/login")
	public String login() {
		return ("login");
	}

	@PostMapping("/login")
	public String loginChecking(@RequestParam("userNameOrEmail") String userNameOrEmail,
			@RequestParam("password") String password, ModelMap model, HttpSession session) {
		User result = userRepository.findByNameOrEmail(userNameOrEmail);
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		if (result == null) {
			model.addAttribute("err", "User not find");
			return "login";
		}
		else if (bcryptPasswordEncoder.matches(password, result.getPassword())) {
			Integer userId = result.getUserId();
			session.setAttribute("userId", userId);
			session.setAttribute("userName", result.getUserName());
			int count = cartRepository.count(userId);
			session.setAttribute("count", count);
			return "redirect:/";
		} else {
			model.addAttribute("err", "Wrong password");
			return "login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	
	public void listCategory(ModelMap map) {
		List<Category> categories = categoryRepository.findAll();
		map.addAttribute("categories", categories);
	}
}
