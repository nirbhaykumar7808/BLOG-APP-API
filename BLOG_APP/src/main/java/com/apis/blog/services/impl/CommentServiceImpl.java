package com.apis.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.blog.Entities.Comment;
import com.apis.blog.Entities.Post;
import com.apis.blog.exceptions.ResourceNotFoundException;
import com.apis.blog.payloads.CommentDto;
import com.apis.blog.repositories.CommentRepo;
import com.apis.blog.repositories.PostRepo;
import com.apis.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post","id",postId)); 
		
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment=this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
		this.commentRepo.delete(comment);
	}

}
