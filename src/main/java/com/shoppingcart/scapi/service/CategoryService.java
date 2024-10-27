package com.shoppingcart.scapi.service;

import com.shoppingcart.scapi.entity.Category;
import com.shoppingcart.scapi.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id) throws CategoryNotFoundException;
    Category getCategoryByName(String name) throws CategoryNotFoundException;
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategoryById(Long id);
}