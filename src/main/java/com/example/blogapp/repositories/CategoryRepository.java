package com.example.blogapp.repositories;

import com.example.blogapp.models.Category;
import com.example.blogapp.models.Label;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {

    Optional<Category> findCategoryByName(String name);
    Optional<Category> findById(Integer id);
    Optional<Category> deleteById(Integer id);

    @Query("From Category c where :label member c.labels")
    List<Category> findAllByLabel(Label label);
}
