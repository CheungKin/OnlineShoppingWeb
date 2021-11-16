package com.cheung.mybatis.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheung.mybatis.model.Address;
import com.cheung.mybatis.model.User;
import com.cheung.mybatis.repository.AddressRepository;
import com.cheung.mybatis.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@GetMapping("/create")
	public String create(ModelMap map) {
		map.addAttribute("user", new User());
		return "createUser";
	}

	@PostMapping("/create")
	public String save(@ModelAttribute("user") User user, @RequestParam("role") String role, ModelMap model) {
		User resultName = userRepository.findByNameOrEmail(user.getUserName());
		User resultEmail = userRepository.findByNameOrEmail(user.getEmail());
		if (resultName == null && resultEmail == null) {
			user.setRole(role);
			BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = bcryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userRepository.save(user);
			return "redirect:/login";
		} else {
			model.addAttribute("err", "The user is existing!");
			return "createUser";
		}
	}

	@GetMapping("update/{userId}")
	public String updatePage(@PathVariable("userId") Integer userId, ModelMap map) {
		User user = userRepository.findById(userId);
		map.addAttribute("user", user);
		List<Address> multiaddress = addressRepository.findByuserId(userId);
		map.addAttribute("multiaddress", multiaddress);
		return "updateUser";
	}

	@PostMapping("/update/{userId}")
	public String update(@ModelAttribute("user") User user, @RequestParam("password")String password, HttpSession session, ModelMap map) {
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = bcryptPasswordEncoder.encode(password);
		user.setPassword(encodedPassword);
		userRepository.update(user);
			return "redirect:/";
		
	}

}
