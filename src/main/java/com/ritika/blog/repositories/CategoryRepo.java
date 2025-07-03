package com.ritika.blog.repositories;

import com.ritika.blog.entities.Category;
import com.ritika.blog.payloads.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {


}
