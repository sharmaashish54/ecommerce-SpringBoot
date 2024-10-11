package com.ecommerce.project.service;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {
  //  List<Category> getAllCategories();
  CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);

  CategoryDTO deleteCategory(Long categoryId);

  CategoryDTO updateCategory(CategoryDTO category,Long categoryId);


}
