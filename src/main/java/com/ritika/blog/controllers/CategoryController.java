package com.ritika.blog.controllers;


import com.ritika.blog.entities.Category;
import com.ritika.blog.payloads.ApiResponse;
import com.ritika.blog.payloads.CategoryDto;
import com.ritika.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory=categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable("catId") Integer catId, @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory=categoryService.updateCategory(categoryDto, catId);
        return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") Integer catId) {
        CategoryDto deletedCategory=categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully",false),HttpStatus.OK);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("catId") Integer catId) {
        CategoryDto category=categoryService.getCategory(catId);
        return new ResponseEntity<CategoryDto>(category,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> category=categoryService.getAllCategory();
        return new ResponseEntity<List<CategoryDto>>(category,HttpStatus.OK);
    }

}
