package com.example.blogapp.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @ManyToMany
    private Set<Label> labels = new HashSet<>();


    public Category(String name, Set<Label> labels) {
        this.name = name;
        this.labels = labels;
    }

    public Category() {

    }

    public Set<Label> getLabel() {
        return labels;
    }

    public void addLabel(Label label) {
        this.labels.add(label);
    }

    public void removeLabel(Label label){ this.labels.remove(label); }

    public void removeAllLabel(){ this.labels.clear(); }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
