package com.apis.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apis.blog.config.AppContants;
import com.apis.blog.payloads.ApiResponse;
import com.apis.blog.payloads.PostDto;
import com.apis.blog.payloads.PostResponse;
import com.apis.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	//Create
	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable int userId,
			@PathVariable int categoryId
			){
		
		PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	//Get by user
	
	@GetMapping("user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable int userId){
		
		List<PostDto> posts=this.postService.getPostByUser(userId);
		
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	
	//Get by category
	
	@GetMapping("category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable int categoryId){
		
		List<PostDto> posts=this.postService.getPostByCategory(categoryId);
		
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	
	//Get by ID
	
	@GetMapping("posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId){
		
		PostDto post=this.postService.getPostById(postId);
		
		return new ResponseEntity<>(post,HttpStatus.OK);
	}
	
	//Get all post
	
	@GetMapping("posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppContants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppContants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppContants.SORT_BY, required = false) String sortBy
			){
		
		PostResponse posts=this.postService.getAllPost(pageNumber, pageSize,sortBy);
		
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
		
	//Delete post
	
	@DeleteMapping("posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId){
		
		this.postService.deletePost(postId);
		
		return new ResponseEntity<>(new ApiResponse("Deleted Successfuly",true),HttpStatus.OK);
	}
	
	//Search post by title
	@GetMapping("post/search/{key}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String key){
		
		List<PostDto> posts=this.postService.searchPost(key);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
}
