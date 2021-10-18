package com.cheung.mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheung.mybatis.model.User;
import com.cheung.mybatis.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String findAll(ModelMap map) {
		List<User> users = userRepository.findAll();
		map.addAttribute("users", users);
		return "ListUsers";
	}
	
	@GetMapping("/{userId}")
	public String findById(@PathVariable("userId") Integer userID, ModelMap map) {
		User user = userRepository.findById(userID);
		map.addAttribute("user", user);
		return "showUser";
	}

	@GetMapping("/create")
	public String create(ModelMap map) {
		map.addAttribute("user", new User());
		return "createUser";
	}

	@PostMapping("/create")
	public String save(@ModelAttribute("user") User user, @RequestParam("role")String role, ModelMap model) {
		User result = userRepository.findByName(user.getUserName());
		if (result != null) {
			model.addAttribute("err", "The user is existing!");
			return "createUser";
		} else {
			user.setRole(role);
			userRepository.save(user);
			return "redirect:/login";
		}

	}

	@GetMapping("update/{userId}")
	public String updatePage(@PathVariable("userId") Integer userId, ModelMap map) {
		User user = userRepository.findById(userId);
		map.addAttribute("user", user);
		return "updateUser";
	}

	@PostMapping("/update/{userId}")
	public String update(@ModelAttribute("user") User user, ModelMap map) {
		userRepository.update(user);
		return "redirect:/users/";
	}

	@GetMapping("/deleteById/{userId}")
	public String deleteById(@PathVariable("userId") Integer userId) {
		userRepository.deleteById(userId);
		return "redirect:/users/";
	}
}
