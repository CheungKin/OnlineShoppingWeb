package com.cheung.mybatis.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cheung.mybatis.model.User;
import com.cheung.mybatis.repository.CartRepository;
import com.cheung.mybatis.repository.UserRepository;

@Controller
public class WebController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login", "user", new User());
	}

	@PostMapping("/login")
	public String loginChecking(@ModelAttribute("user") User user, ModelMap model, HttpSession session) {
		User result = userRepository.findByName(user.getUserName());
		if (result == null) {
			model.addAttribute("err", "User not find");
			return "login";
		} else if (result.getPassword().equals(user.getPassword())) {
			Integer userId = result.getUserId();
			session.setAttribute("userId", userId);
			session.setAttribute("userName", result.getUserName());
			session.setAttribute("role", result.getRole());
			int count = cartRepository.count(userId);
			session.setAttribute("count", count);
			return "redirect:/products/";
		} else {
			model.addAttribute("err", "Wrong password");
			return "login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/products/";
	}
	
}
