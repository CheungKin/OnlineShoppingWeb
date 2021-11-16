package com.cheung.mybatis;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cheung.mybatis.model.User;
import com.cheung.mybatis.repository.CartRepository;
import com.cheung.mybatis.repository.UserRepository;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedInUser.getName();
		User user = userRepository.findByNameOrEmail(userName);
		session.setAttribute("userName", user.getUserName());
		session.setAttribute("userId", user.getUserId());
		int count = cartRepository.count(user.getUserId());
		session.setAttribute("count", count);
		response.sendRedirect("/");
	}

}
