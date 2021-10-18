package com.cheung.mybatis.repository;

import java.util.List;

import com.cheung.mybatis.model.Comment;

public interface CommentRepository {

	public Comment findByCommentId(Integer commentId);

	public List<Comment> findByProductId(Integer productId);
	
	public int save(Comment comment);

	public int deleteById(Integer commentId);
	
}
