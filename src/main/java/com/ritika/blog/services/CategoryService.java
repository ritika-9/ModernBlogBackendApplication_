package com.ritika.blog.services;

import com.ritika.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);


    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);


    public CategoryDto deleteCategory(Integer categoryId);


    public CategoryDto getCategory(Integer categoryId);


    public List<CategoryDto> getAllCategory();


}
