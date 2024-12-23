package com.apis.blog.services;

import com.apis.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,int postId);
	
	void deleteComment(int commentId);
}
