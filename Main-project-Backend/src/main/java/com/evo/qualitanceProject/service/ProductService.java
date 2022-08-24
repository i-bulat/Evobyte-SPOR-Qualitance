package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.model.Review;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ProductService {

    List<Product> getAllProducts();

    Optional<Product> findProduct(Long id);

    Product saveProduct(Product client);

    Product updateProduct(Product client);

    void deleteProduct(Long id);

    List<Product> getTopProducts(Long categoryId);

    List<String> ProductNamesForSearchBar(String s);

    List<Product> ProductsFromSearchBar(String s);

    Map<List<Product>, Integer> findPaginated(long subCategoryId, int pageNumber);

    Long getTotalSales(Long productId);

    List<Product> getRelatedProducts(Long productId);

    Set<Review> getProductReviews(Long productId);

    Set<Review> addReview(Long productId, Review review);

    void deleteReview(Long reviewId, Long productId);

    Set<Review> updateReview(Long reviewId, Long productId, String title, String comment, Integer rating);

    Integer getProductTotalRating(Long productId);
}
