package com.apis.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.apis.blog.Entities.Category;
import com.apis.blog.Entities.Post;
import com.apis.blog.Entities.User;
import com.apis.blog.exceptions.ResourceNotFoundException;
import com.apis.blog.payloads.PostDto;
import com.apis.blog.payloads.PostResponse;
import com.apis.blog.repositories.CategoryRepo;
import com.apis.blog.repositories.PostRepo;
import com.apis.blog.repositories.UserRepo;
import com.apis.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto,int userId,int categoryId) {
		
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		
		
		Post post=this.modelMapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		
		Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePost(int postId) {
		
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy) {
		
		Pageable p= PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		
		Page<Post> pagePost=this.postRepo.findAll(p);
		
		List<Post> posts=pagePost.getContent();
		
		List<PostDto> postDtos=posts.stream().map(
				(post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(int postId) {
		
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(int categoryId) {

		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
		
		List<Post> posts=this.postRepo.findByCategory(category);
		
		List<PostDto> postDtos=posts.stream().map(
				(post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(int userId) {
		
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		List<Post> posts=this.postRepo.findByUser(user);
		
		List<PostDto> postDtos=posts.stream().map(
				(post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String key) {
		
		List<Post> posts=this.postRepo.findByTitleContaining(key);
		
		List<PostDto> postDtos=posts.stream().map(
				(post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
