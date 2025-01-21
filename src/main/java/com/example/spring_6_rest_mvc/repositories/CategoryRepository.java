package com.example.spring_6_rest_mvc.repositories;

import com.example.spring_6_rest_mvc.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
