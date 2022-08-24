package com.evo.qualitanceProject.controller;

import com.evo.qualitanceProject.converter.ProdDetailsConverter;
import com.evo.qualitanceProject.converter.ProductConverter;
import com.evo.qualitanceProject.converter.ReviewConverter;
import com.evo.qualitanceProject.dto.ProdDetailsDto;
import com.evo.qualitanceProject.dto.ProductDto;
import com.evo.qualitanceProject.dto.ProductPage;
import com.evo.qualitanceProject.dto.ReviewDto;
import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.model.ProductSubCategory;
import com.evo.qualitanceProject.model.Review;
import com.evo.qualitanceProject.service.ProductService;
import com.evo.qualitanceProject.service.ProductSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSubCategoryService productSubCategoryService;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private ProdDetailsConverter prodDetailsConverter;

    @Autowired
    private ReviewConverter reviewConverter;

    @GetMapping
    List<ProductDto> getAllProducts() {
        return productConverter.convertModelsToDtos(productService.getAllProducts()).stream()
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable Long id) {
        return productConverter.convertModelToDto(productService.findProduct(id).orElseThrow());
    }

    @GetMapping("/productDetails/{id}")
    ProdDetailsDto getProductDetails(@PathVariable Long id) {
        return prodDetailsConverter.convertModelToDto(productService.findProduct(id).orElseThrow());
    }

    @PostMapping("/addReview/{productId}")
    List<ReviewDto> addReview(@PathVariable Long productId,
                             @RequestBody Review review) {
        return reviewConverter.convertModelsToDtos(productService.addReview(productId, review)).stream()
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteReview/{reviewId}/{productId}")
    ResponseEntity<?> deleteReview(@PathVariable Long reviewId,
                                   @PathVariable Long productId) {
        productService.deleteReview(reviewId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateReview/{reviewId}/{productId}")
    List<ReviewDto> updateReview(@PathVariable Long reviewId,
                             @PathVariable Long productId,
                             @RequestParam String title,
                             @RequestParam String comment,
                             @RequestParam Integer rating) {
        return reviewConverter.convertModelsToDtos(productService.updateReview(reviewId, productId, title,
                comment, rating))
                .stream()
                .collect(Collectors.toList());
    }


    @GetMapping("/getReviews/{productId}")
    List<ReviewDto> getProductReviews(@PathVariable Long productId) {
        return reviewConverter.convertModelsToDtos(productService.getProductReviews(productId)).stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/getProductRating/{productId}")
    Integer getProductTotalRating(@PathVariable Long productId) {
        return productService.getProductTotalRating(productId);
    }

    @GetMapping("/getTopProducts/{categoryId}")
    List<ProductDto> getTopProducts(@PathVariable Long categoryId) {
        return productConverter.convertModelsToDtos(productService.getTopProducts(categoryId)).stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/searchBar/{s}")
    List<String> getProductsForSearchBar(@PathVariable String s) {
        return productService.ProductNamesForSearchBar(s);
    }

    @GetMapping("/productsSearched/{name}")
    List<ProductDto> getProductsFromSearchBar(@PathVariable String name) {
        return productConverter.convertModelsToDtos(productService.ProductsFromSearchBar(name)).stream()
                .collect(Collectors.toList());
    }


    @GetMapping("/totalSales/{productId}")
    Long getTotalSales(@PathVariable Long productId) {
        //number of products sold
        return productService.getTotalSales(productId);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    ProductDto addProduct(@RequestBody com.evo.qualitanceProject.model.Product product) {

        return productConverter.convertModelToDto(productService.saveProduct(product));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    ProductDto updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productConverter.convertModelToDto(productService.updateProduct(product));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ProductPage/{subCategoryId}/{pageNumber}")
    public ProductPage findPaginated(@PathVariable Long subCategoryId, @PathVariable int pageNumber) {
        //fetch the list of products from page
        List<Product> productList = productService.findPaginated(subCategoryId, pageNumber)
                .entrySet()
                .iterator()
                .next()
                .getKey();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productList) {
            productDtos.add(productConverter.convertModelToDto(p));
        }
        ProductSubCategory productSubCategory = productSubCategoryService.findCategory(subCategoryId).orElseThrow();
        ProductPage pageData = new ProductPage(productDtos, productService.findPaginated(subCategoryId, pageNumber).entrySet().iterator().next().getValue(),
                productSubCategory.getProducts().size());

        return pageData;
    }

    @GetMapping("/relatedProducts/{productId}")
    List<ProductDto> getRelatedProducts(@PathVariable Long productId) {
        return productConverter.convertModelsToDtos(productService.getRelatedProducts(productId)).stream()
                .collect(Collectors.toList());
    }

}
