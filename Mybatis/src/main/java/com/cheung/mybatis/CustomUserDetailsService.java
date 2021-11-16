package com.cheung.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import com.cheung.mybatis.model.User;
import com.cheung.mybatis.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByNameOrEmail(userNameOrEmail);
		if (user == null) {
            throw new UsernameNotFoundException("The user is not existing!");
        }
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        String role = user.getRole();
        if (StringUtils.hasText(role)){
            authorityList.add(new SimpleGrantedAuthority(role.trim()));
        }
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorityList);
	}

}
