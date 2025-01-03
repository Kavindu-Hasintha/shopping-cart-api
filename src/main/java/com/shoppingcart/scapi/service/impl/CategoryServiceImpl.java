package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Category;
import com.shoppingcart.scapi.exception.CategoryDeleteFailedException;
import com.shoppingcart.scapi.exception.CategoryNotFoundException;
import com.shoppingcart.scapi.exception.CategoryRetrivedFailedException;
import com.shoppingcart.scapi.exception.CategorySaveFailedException;
import com.shoppingcart.scapi.repo.CategoryRepo;
import com.shoppingcart.scapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        try {
            Category category = categoryRepo.findById(id).get();
            if (category == null) {
                ResponseCode.CATEGORY_NOT_FOUND.setReason("Invalid ID or Category ID does not exist in the database.");
                throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
            }
            return category;
        } catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.CATEGORY_NOT_FOUND.setReason(e.getMessage());
            throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
        }
    }

    @Override
    public Category getCategoryByName(String name) throws CategoryNotFoundException{
        try {
            Category category = categoryRepo.findByName(name);
            if (category == null) {
                ResponseCode.CATEGORY_NOT_FOUND.setReason("Invalid name or Category Name does not exist in the database.");
                throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
            }
            return category;
        } catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.CATEGORY_NOT_FOUND.setReason(e.getMessage());
            throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
        }
    }

    @Override
    public List<Category> getAllCategories() throws CategoryRetrivedFailedException {
        try {
            return categoryRepo.findAll();
        } catch (Exception e) {
            ResponseCode.LIST_CATEGORY_FAIL.setReason(e.getMessage());
            throw new CategoryRetrivedFailedException(ResponseCode.LIST_CATEGORY_FAIL);
        }
    }

    @Override
    public Category addCategory(Category category) throws CategorySaveFailedException {
        try {
            boolean isExists = categoryRepo.existsByName(category.getName());
            if (isExists) {
                ResponseCode.CREATE_CATEGORY_FAIL.setReason("Category already exists in the database.");
                throw new CategorySaveFailedException(ResponseCode.CREATE_CATEGORY_FAIL);
            }
            return categoryRepo.save(category);
        } catch (CategorySaveFailedException e) {
            throw new CategorySaveFailedException(ResponseCode.CREATE_CATEGORY_FAIL);
        } catch (Exception e) {
            ResponseCode.CREATE_CATEGORY_FAIL.setReason(e.getMessage());
            throw new CategorySaveFailedException(ResponseCode.CREATE_CATEGORY_FAIL);
        }
    }

    @Override
    public Category updateCategory(Category category, Long id) throws CategoryNotFoundException, CategorySaveFailedException {
        try {
            Category category1 = categoryRepo.findById(id).get();
            if (category1 == null) {
                ResponseCode.CATEGORY_NOT_FOUND.setReason("Invalid ID or Category ID does not exist in the database.");
                throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
            }
            category1.setName(category.getName());
            return categoryRepo.save(category1);
        } catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.UPDATE_CATEGORY_FAIL.setReason(e.getMessage());
            throw new CategorySaveFailedException(ResponseCode.UPDATE_CATEGORY_FAIL);
        }
    }

    @Override
    public void deleteCategoryById(Long id) throws CategoryNotFoundException, CategoryDeleteFailedException {
        try {
            Category category = categoryRepo.findById(id).get();
            if (category == null) {
                ResponseCode.CATEGORY_NOT_FOUND.setReason("Invalid ID or Category ID does not exist in the database.");
                throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
            }
            categoryRepo.delete(category);
        } catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException(ResponseCode.CATEGORY_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.DELETE_CATEGORY_FAIL.setReason(e.getMessage());
            throw new CategoryNotFoundException(ResponseCode.DELETE_CATEGORY_FAIL);
        }
    }
}
