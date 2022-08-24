package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.AppUser;
import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.model.ProductSubCategory;
import com.evo.qualitanceProject.model.Review;
import com.evo.qualitanceProject.repository.ProductRepository;
import com.evo.qualitanceProject.repository.ProductSubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductSubCategoryRepository subCategoryRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Product> getAllProducts() {
        List<Product> allProducts = repository.findAll();
        return allProducts;
    }

    @Override
    public Optional<Product> findProduct(Long id) {
        return repository.findById(id);

    }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {
        Product updateProduct = repository.findById(product.getId()).orElseThrow();
        updateProduct.setDescription(product.getDescription());
        updateProduct.setImageURL(product.getImageURL());
        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setQuantity(product.getQuantity());
        ProductSubCategory subCategory = new ProductSubCategory();
        subCategory.setId(product.getSubCategory().getId());
        updateProduct.setSubCategory(subCategory);
        return updateProduct;
    }

    public List<Product> findProductsForSearchbar(String s) {
        return repository.findAllByName(s);
    }

    public List<String> ProductNamesForSearchBar(String s) {
        List<Product> products = findProductsForSearchbar(s);
        List<String> resultList = new ArrayList<>();
        int count = 0;
        for (Product p : products) {
            if (count < 10) {
                resultList.add(p.getName());
                count++;
            } else {
                return resultList;
            }
        }
        return resultList;
    }


    public List<Product> ProductsFromSearchBar(String s) {
        return repository.findProductsAccordingToSearchBar(s, s);
    }

    @Override
    public Map<List<Product>, Integer> findPaginated(long subCategoryId, int pageNumber) {
        // the number of elements displayed on the page
        int pageSize = 60;

        //We get the products of the specific subCategory
        ProductSubCategory subCategory = subCategoryRepository.findByIdWithProduct(subCategoryId).get();
        List<Product> productList = new ArrayList<>(subCategory.getProducts());
        //Adjusting the page
        PagedListHolder page = new PagedListHolder(productList);
        MutableSortDefinition x = new MutableSortDefinition("name", true, true);
        page.setSort(x);
        page.resort();
        page.setPageSize(pageSize); // number of items per page
        page.setPage(pageNumber - 1); //current page

        Map<List<Product>, Integer> map = new HashMap<>();
        map.put(page.getPageList(), page.getPageCount());

        return map;
    }

    @Override
    public Long getTotalSales(Long productId) {
        return repository.getTotalSales(productId);
    }

    @Override
    public List<Product> getRelatedProducts(Long productId) {
        ProductSubCategory subCategory = repository.findById(productId).orElseThrow().getSubCategory();

        List<Product> products = repository.getProductsBySubcategory(subCategory);
        Collections.shuffle(products);

        if (products.size() < 10) {
            return products.subList(0, products.size());
        }
        return products.subList(0, 10);
    }

    @Override
    public Set<Review> getProductReviews(Long productId) {
        return repository.getByIdWithReviews(productId).getReviews();
    }

    @Override
    @Transactional
    public Set<Review> addReview(Long productId, Review review) {

        AppUser user = userService.getAuthenticatedUser();

        Set<Review> reviews = repository.findById(productId).orElseThrow().getReviews();

        review.setDateCreated(LocalDate.now());
        review.setUser(user);
        reviews.add(review);

        return reviews;
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, Long productId) {
        Set<Review> reviews = findProduct(productId).orElseThrow().getReviews();

        for (Iterator<Review> iterator = reviews.iterator(); iterator.hasNext(); ) {
            Review rev = iterator.next();
            if (rev.getId() == reviewId) {
                iterator.remove();
            }
        }

    }

    @Override
    @Transactional
    public Set<Review> updateReview(Long reviewId, Long productId, String title,
                                    String comment, Integer rating) {
        Set<Review> reviews = findProduct(productId).orElseThrow().getReviews();

        for (Review rev : reviews) {
            if (rev.getId() == reviewId) {
                rev.setTitle(title);
                rev.setComment(comment);
                rev.setRating(rating);
                break;
            }
        }

        return reviews;
    }

    @Override
    public Integer getProductTotalRating(Long productId) {
        //do we need precision value?

        Set<Review> reviews = getProductReviews(productId);
        int ratingSum = 0;
        int ratingNr = 0;
        for (Review rev : reviews) {
            if (!rev.getRating().equals(null)) {
                ratingSum += rev.getRating();
                ratingNr++;
            }
        }
        if (ratingNr > 0)
            return ratingSum / ratingNr;
        else return 0;
    }


    @Override
    public List<Product> getTopProducts(Long categoryId) {
        LocalDate date = LocalDate.now().minusDays(2);
        Pageable page = PageRequest.of(0, 10);
        return repository.getTopProducts(date, categoryId, page);
    }


}
