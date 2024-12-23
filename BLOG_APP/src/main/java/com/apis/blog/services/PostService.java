package com.apis.blog.services;

import java.util.List;

import com.apis.blog.payloads.PostDto;
import com.apis.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,int categoryId,int userId);
	
	PostDto updatePost(PostDto postDto,int postId);
	
	void deletePost(int postId);
	
	PostResponse getAllPost(int pageNumber,int pageSize,String sortBy);
	
	PostDto getPostById(int postId);
	
	List<PostDto> getPostByCategory(int categoryId);
	
	List<PostDto> getPostByUser(int userId);
	
	List<PostDto> searchPost(String key);
}
