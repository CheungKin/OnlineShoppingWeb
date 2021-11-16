package com.cheung.mybatis.model;

import lombok.Data;

@Data
public class Comment {
	private int commentId;
	private int userId;
	private int productId;
	private String userComment;
	private User user;
}
