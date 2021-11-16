package com.cheung.mybatis.model;

import lombok.Data;

@Data
public class Address {
	private int addressId;
	private int userId;
	private String area;
	private String address;
	private String phone;
	private String receiver;
	private boolean isDefault;
}
