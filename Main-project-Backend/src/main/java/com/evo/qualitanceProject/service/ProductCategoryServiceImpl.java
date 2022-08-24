package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.ProductCategory;
import com.evo.qualitanceProject.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;


    @Override
    public List<ProductCategory> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public Optional<ProductCategory> findCategory(Long id) {
        Optional<ProductCategory> category = repository.findById(id);

        return category;
    }

    @Override
    public ProductCategory saveCategory(ProductCategory category) {
        return repository.save(category);
    }


    @Override
    @Transactional
    public ProductCategory updateCategory(ProductCategory category) {
        ProductCategory updateCategory = repository.findById(category.getId()).orElseThrow();
        updateCategory.setDescription(category.getDescription());
        updateCategory.setName(category.getName());
        return updateCategory;
    }

    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProductCategory> getTopCategories() {
        LocalDate date = LocalDate.now().minusWeeks(1);
        Pageable page = PageRequest.of(0, 5);
        return repository.getTopCategories(date, page);
    }
}
