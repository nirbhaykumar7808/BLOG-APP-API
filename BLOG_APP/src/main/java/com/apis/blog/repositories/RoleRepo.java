package com.apis.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apis.blog.Entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
