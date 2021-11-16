package com.cheung.mybatis.repository;

import java.util.List;

import com.cheung.mybatis.model.User;

public interface UserRepository {
	
	public List<User> findAll();

	public User findById(Integer userId);
	
	public User findByName(String userName);
	
	public User findByNameOrEmail(String userNameOrEmail);

	public int save(User user);

	public int update(User user);

	public int deleteById(Integer userId);
	
	public List<User> search(String name);
}
