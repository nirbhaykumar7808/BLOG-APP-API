package com.apis.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apis.blog.payloads.ApiResponse;
import com.apis.blog.payloads.CommentDto;
import com.apis.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto,
			@PathVariable int postId
			){
		
		CommentDto createdCommentDto=this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<>(createdCommentDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable int commentId
			){
		
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment Deleted", false),HttpStatus.OK);
	}
}
