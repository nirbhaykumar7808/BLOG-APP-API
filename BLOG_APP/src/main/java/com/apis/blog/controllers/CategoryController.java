package com.apis.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apis.blog.payloads.ApiResponse;
import com.apis.blog.payloads.CategoryDto;
import com.apis.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto crateCategory=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(crateCategory,HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer catId){
		CategoryDto updateCategory=this.categoryService.updateCategory(categoryDto,catId);
		return new ResponseEntity<>(updateCategory,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<>(new ApiResponse("Deleted Successfuly",true),HttpStatus.OK);
	}
	
	//get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> deleteCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer catId){
		CategoryDto catDto=this.categoryService.getCategoryById(catId);
		return new ResponseEntity<>(catDto,HttpStatus.OK);
	}
	
	//get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		return ResponseEntity.ok(this.categoryService.getAllCategory());
	}
	
	
	
	
	
}
