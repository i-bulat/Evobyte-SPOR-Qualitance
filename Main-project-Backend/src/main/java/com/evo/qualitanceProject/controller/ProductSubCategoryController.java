package com.evo.qualitanceProject.controller;

import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.model.ProductCategory;
import com.evo.qualitanceProject.model.ProductSubCategory;
import com.evo.qualitanceProject.service.ProductCategoryService;
import com.evo.qualitanceProject.service.ProductSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/subcategories")
public class ProductSubCategoryController {

    @Autowired
    private ProductSubCategoryService categoryService;

    @Autowired
    private ProductCategoryService service;


    @GetMapping
    List<ProductSubCategory> getAllSubCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    ProductSubCategory getSubCategoryById(@PathVariable Long id) {
        return categoryService.findCategory(id).get();
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    ProductSubCategory addSubCategory(@RequestBody ProductSubCategory category) {
        return categoryService.saveCategory(category);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    ProductSubCategory updateSubCategory(@PathVariable Long id, @RequestBody ProductSubCategory category) {
        category.setId(id);
        return categoryService.updateCategory(category);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSubProduct(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
