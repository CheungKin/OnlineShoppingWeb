package com.cheung.mybatis.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheung.mybatis.model.Address;
import com.cheung.mybatis.model.Cart;
import com.cheung.mybatis.model.Order;
import com.cheung.mybatis.model.OrderDetail;
import com.cheung.mybatis.model.Product;
import com.cheung.mybatis.model.User;
import com.cheung.mybatis.repository.AddressRepository;
import com.cheung.mybatis.repository.CartRepository;
import com.cheung.mybatis.repository.OrderDetailRepository;
import com.cheung.mybatis.repository.OrderRepository;
import com.cheung.mybatis.repository.ProductRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AddressRepository addressRepository;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@GetMapping("/add/{cartId}")
	public String addOrder(@PathVariable("cartId") Integer cartId, HttpSession session, ModelMap map) {
		Cart cart = cartRepository.findBycartId(cartId);
		map.addAttribute("carts", cart);
		map.addAttribute("total", cart.getCost());
		session.setAttribute("cartId", cartId);
		Integer userId = (Integer) session.getAttribute("userId");
		List<Address> mulitaddress = addressRepository.findByuserId(userId);
		map.addAttribute("multiaddress", mulitaddress);
		return "addOrder";
	}

	@GetMapping("/add")
	public String addOrders(HttpSession session, ModelMap map) {
		Integer userId = (Integer) session.getAttribute("userId");
		List<Cart> carts = cartRepository.findByuserId(userId);
		map.addAttribute("carts", carts);
		map.addAttribute("total", cartRepository.total(userId));
		List<Address> mulitaddress = addressRepository.findByuserId(userId);
		map.addAttribute("multiaddress", mulitaddress);
		return "addOrder";
	}

	// Add order detail
	public void addOrderDetail(int productId, int quantity, int orderId, int cost) {
		Product product = productRepository.findById(productId);
		int stock = product.getStock();
		int result = stock - quantity;
		product.setStock(result);
		productRepository.update(product);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProductId(productId);
		orderDetail.setOrderId(orderId);
		orderDetail.setQuantity(quantity);
		orderDetail.setCost(cost);
		orderDetailRepository.save(orderDetail);
	}

	@PostMapping("/comfirm")
	public String comfirm(@RequestParam("total") Integer total, @RequestParam("delivery") Date delivery,
			@RequestParam("addressId") int addressId, HttpSession session, ModelMap map) {
		Integer userId = (Integer) session.getAttribute("userId");
		Order order = new Order();
		order.setUserId(userId);
		order.setTotal(total);
		order.setDelivery(delivery);
		order.setAddressId(addressId);
		order.setStatus("processing");
		orderRepository.save(order);
		int orderId = order.getOrderId();
		Integer cartId = (Integer) session.getAttribute("cartId");
		if (cartId == null) {
			List<Cart> carts = cartRepository.findByuserId(userId);
			for (int i = 0; i < carts.size(); i++) {
				addOrderDetail(carts.get(i).getProduct().getProductId(), carts.get(i).getQuantity(), orderId,
						carts.get(i).getCost());
			}
			cartRepository.deleteByuserId(userId);
			int count = cartRepository.count(userId);
			session.setAttribute("count", count);
			session.removeAttribute("cartId");
		} else {
			Cart cart = cartRepository.findBycartId(cartId);
			addOrderDetail(cart.getProduct().getProductId(), cart.getQuantity(), orderId, cart.getCost());
			cartRepository.deleteBycartId(cartId);
			int count = cartRepository.count(userId);
			session.setAttribute("count", count);
			session.removeAttribute("cartId");
		}
		return "redirect:/order/";
	}

	@GetMapping("/")
	public String showOrder(HttpServletRequest request, HttpSession session,@RequestParam(value = "pageNo", defaultValue = "1") int pageNo, ModelMap map) {
		PageHelper.startPage(pageNo, 8);
		List<Order> orders = null;
		if (request.isUserInRole("ROLE_ADMIN")) {
			orders = orderRepository.findAll();
		} else {
			Integer userId = (Integer) session.getAttribute("userId");
			orders = orderRepository.findByuserId(userId);
		}
		PageInfo<Order> page = new PageInfo<Order>(orders);
		map.addAttribute("orders", orders);
		map.addAttribute("pageNo", pageNo);
		map.addAttribute("page", page);
		return "order";
	}

	@GetMapping("/{orderId}/")
	public String showDetail(@PathVariable("orderId") Integer orderId, ModelMap map) {
		List<OrderDetail> orderDetails = orderDetailRepository.findByorderId(orderId);
		map.addAttribute("orderDetails", orderDetails);
		return "showDetail";
	}

	@PostMapping("/update")
	public String update(@RequestParam("orderId") Integer orderId, @RequestParam("status") String status) {
		Order order = new Order();
		order.setOrderId(orderId);
		order.setStatus(status);
		orderRepository.update(order);
		return "redirect:/order/";
	}

	@GetMapping("/delete/{orderId}")
	public String delete(@PathVariable("orderId") Integer orderId) {
		orderDetailRepository.delete(orderId);
		orderRepository.delete(orderId);
		return "redirect:/order/";
	}

}
