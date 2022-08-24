package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.model.ProductCategory;
import com.evo.qualitanceProject.model.ProductSubCategory;

import java.util.List;
import java.util.Optional;

public interface ProductSubCategoryService {
    List<ProductSubCategory> getAllCategories();

    Optional<ProductSubCategory> findCategory(Long id);

    ProductSubCategory saveCategory(ProductSubCategory category);

    ProductSubCategory updateCategory(ProductSubCategory category);

    void deleteCategory(Long id);


}
