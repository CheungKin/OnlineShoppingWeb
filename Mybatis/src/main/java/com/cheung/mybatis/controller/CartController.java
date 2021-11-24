package com.cheung.mybatis.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheung.mybatis.model.Cart;
import com.cheung.mybatis.model.Product;
import com.cheung.mybatis.repository.CartRepository;
import com.cheung.mybatis.repository.ProductRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/{productId}")
	public String add(@PathVariable("productId") int productId, @RequestParam("quantity") String quantity,
			HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		Product product = productRepository.findById(productId);
		int amount = Integer.parseInt(quantity);
		Cart result = cartRepository.unique(userId, productId);
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setProductId(productId);
		cart.setQuantity(amount);
		int price = product.getPrice();
		cart.setCost(amount * price);
		if (result == null) {
			cartRepository.save(cart);
			int count = cartRepository.count(userId);
			session.setAttribute("count", count);
		} else {
			cart.setCartId(result.getCartId());
			cartRepository.update(cart);
		}
		return "redirect:/cart/";
	}

	@GetMapping("/")
	public String show(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,ModelMap map, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		PageHelper.startPage(pageNo,8);
		List<Cart> carts = cartRepository.findByuserId(userId);
		if (carts.isEmpty()) {
			map.addAttribute("empty", "Your cart is empty");
		} else {
			PageInfo<Cart> page = new PageInfo<Cart>(carts);
			map.addAttribute("carts", carts);
			map.addAttribute("pageNo", pageNo);
			map.addAttribute("page", page);
			map.addAttribute("total", cartRepository.total(userId));
		}
		return "cart";
	}

	@PostMapping("/update/")
	public String update(@ModelAttribute("cart") Cart cart, ModelMap map) {
		Product product = productRepository.findById(cart.getProductId());
		Cart result = cartRepository.findBycartId(cart.getCartId());
		int price = product.getPrice();
		int quantity = cart.getQuantity();
		result.setQuantity(quantity);
		result.setCost(price * quantity);
		cartRepository.update(result);
		return "redirect:/cart/";
	}

	@GetMapping("/delete/{cartId}")
	public String delete(@PathVariable("cartId") int cartId, HttpSession session) {
		cartRepository.deleteBycartId(cartId);
		Integer userId = (Integer) session.getAttribute("userId");
		int count = cartRepository.count(userId);
		session.setAttribute("count", count);
		return "redirect:/cart/";
	}
}
