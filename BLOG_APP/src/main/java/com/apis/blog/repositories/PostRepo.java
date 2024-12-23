package com.apis.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apis.blog.Entities.Category;
import com.apis.blog.Entities.Post;
import com.apis.blog.Entities.User;


public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
}
