package com.cheung.mybatis.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.ModelAndView;

import com.cheung.mybatis.model.Comment;
import com.cheung.mybatis.model.Product;
import com.cheung.mybatis.repository.CartRepository;
import com.cheung.mybatis.repository.CommentRepository;
import com.cheung.mybatis.repository.OrderRepository;
import com.cheung.mybatis.repository.ProductRepository;

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
	private CartRepository cartRepository;

	@GetMapping("/")
	public String findAll(ModelMap map, HttpSession session) {
		List<Product> products = productRepository.findAll();
		map.addAttribute("products", products);
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId!=null) {
			int count = cartRepository.count(userId);
			map.addAttribute("count", count);
		}
		return ("index");
	}

	@GetMapping("/{id}")
	public String findById(@PathVariable("id") Integer productId, HttpSession session, ModelMap map) {
		Product product = productRepository.findById(productId);
		List<Comment> comments = commentRepository.findByProductId(productId);
		map.addAttribute("product", product);
        map.addAttribute("comments", comments);
        Integer userId = (Integer)session.getAttribute("userId");
        boolean showComment = (orderRepository.findByBothId(userId, productId).isEmpty())? false : true; 
        map.addAttribute("showComment", showComment);
        return "productDetail";
	}
	
	@GetMapping("/create")
	public ModelAndView create() {
		return new ModelAndView("createProduct", "product", new Product());
	}

	@PostMapping("/create")
	public String create(@ModelAttribute("product") Product product, @RequestParam("image") MultipartFile image,
			ModelMap map) throws IllegalStateException, IOException {
		try {
			String path = StringUtils.cleanPath(image.getOriginalFilename());
			product.setPhoto("/images/" + path);
			String filePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
			File filepath = new File(filePath + path);
			image.transferTo(filepath);
		} catch (IOException e) {
			product.setPhoto("/images/NOPhoto.jpeg");
		}
		productRepository.save(product);
		return "redirect:/products/";
	}

	@GetMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Integer productId) {
		Product product = productRepository.findById(productId);
		return new ModelAndView("updateProduct", "product", product);
	}

	@PostMapping("/update/{id}")
	public String update(@RequestParam("productId") int productId, @RequestParam("productName") String productName,
			@RequestParam("category") String category, @RequestParam("stock") int stock,
			@RequestParam("price") int price, @RequestParam("image") MultipartFile image, @RequestParam("photo") String photo, ModelMap map)
			throws IllegalStateException, IOException {
		Product product = new Product();
		product.setProductId(productId);
		product.setProductName(productName);
		product.setCategory(category);
		product.setStock(stock);
		product.setPrice(price);
		try {
			String path = StringUtils.cleanPath(image.getOriginalFilename());
			product.setPhoto("/images/" + path);
			String filePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
			File filepath = new File(filePath + path);
			image.transferTo(filepath);
			productRepository.update(product);
		} catch (IOException e) {
			product.setPhoto(photo);
			productRepository.update(product);
		}
		return "redirect:/products/" + productId;
	}

	@GetMapping("deleteById/{id}")
	public String deleteById(@PathVariable("id") Integer productId) {
		productRepository.deleteById(productId);
		return "redirect:/products/";
	}
	
	@PostMapping("/addComment")
	public String addComment(@RequestParam("productId") int productId, @RequestParam("userComment") String userComment, HttpSession session, ModelMap map) {
		Comment comment = new Comment();
		comment.setUserId((Integer)session.getAttribute("userId"));
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
}
