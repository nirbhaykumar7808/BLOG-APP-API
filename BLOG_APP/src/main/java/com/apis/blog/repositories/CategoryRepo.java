package com.apis.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apis.blog.Entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
