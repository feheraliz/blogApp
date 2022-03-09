package com.example.blogapp.repositories;

import com.example.blogapp.models.BlogPost;
import com.example.blogapp.models.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BlogPostRepository extends CrudRepository<BlogPost, String> {
    Optional<BlogPost> findById(Integer id);

    Optional<BlogPost> deleteById(Integer id);

    @Query("From BlogPost b where :category member b.categories")
    List<BlogPost> findAllByCategories(Category category);
}
