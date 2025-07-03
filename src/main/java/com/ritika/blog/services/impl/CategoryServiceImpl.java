package com.ritika.blog.services.impl;

import com.ritika.blog.entities.Category;
import com.ritika.blog.exceptions.ResourceNotFoundException;
import com.ritika.blog.payloads.CategoryDto;
import com.ritika.blog.repositories.CategoryRepo;
import com.ritika.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category cat=this.modelMapper.map(categoryDto, Category.class);
        Category category=this.categoryRepo.save(cat);
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedcat= this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedcat, CategoryDto.class);
    }

    @Override
    public CategoryDto deleteCategory(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        this.categoryRepo.delete(cat);
        return null;
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));

        return this.modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> cats=this.categoryRepo.findAll();
        return cats.stream().map(cat-> this.modelMapper.map(cat, CategoryDto.class)).toList();
    }
}
