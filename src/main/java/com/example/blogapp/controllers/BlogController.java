package com.example.blogapp.controllers;

import com.example.blogapp.models.BlogPost;
import com.example.blogapp.models.Category;
import com.example.blogapp.models.Label;
import com.example.blogapp.repositories.BlogPostRepository;
import com.example.blogapp.repositories.CategoryRepository;
import com.example.blogapp.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BlogController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LabelRepository labelRepository;

    @GetMapping("/blogpost/{labelId}")
    public ResponseEntity<?> getBlogpostByLabel( @PathVariable("labelId") Integer labelId){
        Optional<Label> label = this.labelRepository.findById(labelId);
        List<Category> categories = this.categoryRepository.findAllByLabel(label.get());
        List<BlogPost> result = new ArrayList<>();
        for (Category c : categories){
            List<BlogPost> blogPosts = this.blogPostRepository.findAllByCategories(c);
            for(BlogPost b : blogPosts){
                if(!result.contains(b)){
                    result.add(b);
                }
            }
        }
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/blogpost")
    public ResponseEntity<?> createBlogPost(@RequestBody BlogPost blogPost){
        blogPost.setPostTime(new Timestamp(System.currentTimeMillis()));
        blogPost.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        this.blogPostRepository.save(blogPost);
        return ResponseEntity
                .ok()
                .body(blogPost);
    }

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        this.categoryRepository.save(category);
        return ResponseEntity
                .ok()
                .body(category);
    }

    @PostMapping("/label")
    public ResponseEntity<?> createLabel(@RequestBody Label label){
        this.labelRepository.save(label);
        return ResponseEntity
                .ok()
                .body(label);
    }


    @PutMapping("/blogpost/{id}")
    public ResponseEntity<?> updateBlogPost(@PathVariable("id") Integer id, @RequestBody BlogPost blogPostDetails) {
        Optional<BlogPost> blogPost = this.blogPostRepository.findById(id);
        blogPost.get().setTitle(blogPostDetails.getTitle());
        blogPost.get().setText(blogPostDetails.getText());
        blogPost.get().setLastUpdate(new Timestamp(System.currentTimeMillis()));
        this.blogPostRepository.save(blogPost.get());
        return ResponseEntity
                .ok()
                .body(blogPost);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategoryName(@PathVariable("id") Integer id, @RequestBody Category categoryDetails) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if(!category.get().getName().equals(categoryDetails.getName())) {
            Optional<Category> checkNameExist = this.categoryRepository.findCategoryByName(categoryDetails.getName());
            if (checkNameExist.isEmpty()){
                category.get().setName(categoryDetails.getName());
            }
        }
        this.categoryRepository.save(category.get());
        return ResponseEntity
                .ok()
                .body(category);
    }


    @PutMapping("/blogpost/{id}/addcategory/{categoryId}")
    public ResponseEntity<?> addCategoryToBlogpost(@PathVariable("id") Integer id, @PathVariable("categoryId") Integer categoryId) {
        Optional<BlogPost> blogPost = this.blogPostRepository.findById(id);
        if(blogPost.get().getCategory().size() >= 5){
            return ResponseEntity
                    .badRequest()
                    .body("Error: Can't be more than 5 categories per blog post!");
        }
        else{
            Optional<Category> category = this.categoryRepository.findById(categoryId);
            blogPost.get().addCategory(category.get());
        }
        this.blogPostRepository.save(blogPost.get());
        return ResponseEntity
                .ok()
                .body(blogPost);
    }

    @PutMapping("/blogpost/{id}/removecategory/{categoryId}")
    public ResponseEntity<?> removeCategoryFromBlogpost(@PathVariable("id") Integer id, @PathVariable("categoryId") Integer categoryId) {
        Optional<BlogPost> blogPost = this.blogPostRepository.findById(id);
        Optional<Category> category = this.categoryRepository.findById(categoryId);
        if(blogPost.get().getCategory().contains(category.get())){
            blogPost.get().removeCategory(category.get());
        }
        this.blogPostRepository.save(blogPost.get());
        return ResponseEntity
                .ok()
                .body(category);
    }


    @PutMapping("/category/{id}/addlabel/{labelId}")
    public ResponseEntity<?> addLabelToCategory(@PathVariable("id") Integer id, @PathVariable("labelId") Integer labelId) {
        Optional<Category> category = this.categoryRepository.findById(id);
        Optional<Label> label = this.labelRepository.findById(labelId);
        category.get().addLabel(label.get());
        this.categoryRepository.save(category.get());
        return ResponseEntity
                .ok()
                .body(category);
    }

    @PutMapping("/category/{id}/removelabel/{labelId}")
    public ResponseEntity<?> removeLabelFromCategory(@PathVariable("id") Integer id, @PathVariable("labelId") Integer labelId) {
        Optional<Category> category = this.categoryRepository.findById(id);
        Optional<Label> label = this.labelRepository.findById(labelId);
        if(category.get().getLabel().contains(label.get())){
            category.get().removeLabel(label.get());
        }
        this.categoryRepository.save(category.get());
        return ResponseEntity
                .ok()
                .body(category);
    }


    @Transactional
    @DeleteMapping("/blogpost/{id}")
    public ResponseEntity<?> deleteBlogPost(@PathVariable(value = "id") Integer id){
        this.blogPostRepository.deleteById(id);
        return ResponseEntity
                .ok()
                .body("Blog post deleted successfully!");
    }

    @Transactional
    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Integer id){
        Optional<Category> category = this.categoryRepository.findById(id);
        List<BlogPost> blogPosts = this.blogPostRepository.findAllByCategories(category.get());
        for (BlogPost b : blogPosts){
            b.removeCategory(category.get());
        }
        this.categoryRepository.deleteById(id);

        return ResponseEntity
                .ok()
                .body("Category deleted successfully!");
    }

    @Transactional
    @DeleteMapping("/label/{id}")
    public ResponseEntity<?> deleteLabel(@PathVariable(value = "id") Integer id){
        Optional<Label> label = this.labelRepository.findById(id);
        List<Category> categories = this.categoryRepository.findAllByLabel(label.get());
        for (Category c : categories ){
            c.removeLabel(label.get());
        }
        this.labelRepository.deleteById(id);
        return ResponseEntity
                .ok()
                .body("Category deleted successfully!");
    }
}
