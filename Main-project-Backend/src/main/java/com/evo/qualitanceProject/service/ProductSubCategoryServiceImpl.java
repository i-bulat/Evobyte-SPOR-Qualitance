package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.model.ProductCategory;
import com.evo.qualitanceProject.model.ProductSubCategory;
import com.evo.qualitanceProject.repository.ProductSubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSubCategoryServiceImpl implements ProductSubCategoryService {

    @Autowired
    private ProductSubCategoryRepository repository;


    @Override
    public List<ProductSubCategory> getAllCategories() {
        return repository.findAllWithProduct();
    }

    @Override
    public Optional<ProductSubCategory> findCategory(Long id) {
        return repository.findByIdWithProduct(id);

    }

    @Override
    public ProductSubCategory saveCategory(ProductSubCategory category) {
        return repository.save(category);
    }

    @Override
    @Transactional
    public ProductSubCategory updateCategory(ProductSubCategory category) {
        ProductSubCategory updateCategory = repository.findByIdWithProduct(category.getId()).orElseThrow();
        updateCategory.setDescription(category.getDescription());
        updateCategory.setName(category.getName());
        ProductCategory productCategory = new ProductCategory(category.getProductCategory().getId());
        updateCategory.setProductCategory(productCategory);
        return updateCategory;
    }


    @Override
    @Transactional
    public void deleteCategory(Long id) {
        repository.deleteByIdWithProduct(id);
    }
}
