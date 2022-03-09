package com.example.blogapp.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BlogPost {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String text;
    private Timestamp postTime;
    private Timestamp lastUpdate;

    @Size(max = 5)
    @ManyToMany
    private Set<Category> categories = new HashSet<Category>();


    public BlogPost(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public BlogPost() {

    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    public Set<Category> getCategory() {
        return categories;
    }

    public void removeAllCategory(){ this.categories.clear(); }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
