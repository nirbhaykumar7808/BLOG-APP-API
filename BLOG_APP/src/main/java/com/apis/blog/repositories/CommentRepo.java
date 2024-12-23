package com.apis.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apis.blog.Entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
