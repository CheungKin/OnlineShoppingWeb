package com.cheung.mybatis.model;

import lombok.Data;

@Data
public class User {
	private int userId;
	private String userName;
	private String password;
	private String address;
	private String role;
}
