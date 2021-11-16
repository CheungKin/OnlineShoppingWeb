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

import com.cheung.mybatis.model.Address;
import com.cheung.mybatis.repository.AddressRepository;

@Controller
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;

	@GetMapping("/add")
	public String address(ModelMap map) {
		map.addAttribute("address", new Address());
		return "addAddress";
	}

	@PostMapping("/add")
	public String addAddress(@ModelAttribute("address") Address address, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		address.setUserId(userId);
		addressRepository.save(address);
		return "redirect:/users/update/" + userId;
	}

	@GetMapping("/")
	public String showAddress(HttpSession session, ModelMap map) {
		Integer userId = (Integer) session.getAttribute("userId");
		List<Address> multiaddress = addressRepository.findByuserId(userId);
		map.addAttribute("multiaddress", multiaddress);
		return "showAddress";
	}

	@GetMapping("/update/{addressId}")
	public String update(@PathVariable("addressId") int addressId, ModelMap map) {
		Address address = addressRepository.findByAddressId(addressId);
		map.addAttribute("address", address);
		return "updateAddress";
	}

	@PostMapping("/update/{addressId}")
	public String updateAddress(@ModelAttribute("address") Address address, HttpSession session) {
		addressRepository.update(address);
		Integer userId = (Integer) session.getAttribute("userId");
		return "redirect:/users/update/" + userId;
	}

	@GetMapping("/delete/{addressId}")
	public String delete(@PathVariable("addressId") int addressId, HttpSession session) {
		addressRepository.delete(addressId);
		Integer userId = (Integer) session.getAttribute("userId");
		return "redirect:/users/update/" + userId;
	}
	
	
	@PostMapping("/default")
	public String isDefault (@RequestParam("addressId") int addressId, @RequestParam("isDefault") boolean isDefault, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		addressRepository.changeToFalse(userId);
		addressRepository.changeToTrue(addressId);
		return "redirect:/users/update/" + userId;
	}
	
}
