package com.evo.qualitanceProject.controller;

import com.evo.qualitanceProject.model.ProductCategory;
import com.evo.qualitanceProject.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping
    List<ProductCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    ProductCategory getByCategory(@PathVariable Long id) {
        return categoryService.findCategory(id).get();
    }


    @GetMapping("/getTopCategories")
    List<ProductCategory> getTopCategories() {
        return categoryService.getTopCategories();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    ProductCategory addCategory(@RequestBody ProductCategory category) {
        return categoryService.saveCategory(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    ProductCategory updateCategory(@PathVariable Long id, @RequestBody ProductCategory category) {
        category.setId(id);
        return categoryService.updateCategory(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
