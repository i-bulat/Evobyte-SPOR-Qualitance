package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();

    Optional<ProductCategory> findCategory(Long id);

    ProductCategory saveCategory(ProductCategory category);

    ProductCategory updateCategory(ProductCategory category);

    void deleteCategory(Long id);

    List<ProductCategory> getTopCategories();

}
