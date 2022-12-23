package be.yorian.budgetmonitor.controller;

import be.yorian.budgetmonitor.entity.Category;
import be.yorian.budgetmonitor.response.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryController {

    List<Category> getCategories();
    Optional<Category> getCategoryById(long id);
    ResponseEntity<CustomResponse> getCommentBySearchterm(Optional<String> label,
                                                          Optional<Integer> page,
                                                          Optional<Integer> size);
    ResponseEntity<Void> saveCategory(Category category);
    Category updateCategory(Long categoryId, Category category);
    void deleteCategory(long category_id);
        
    
}
