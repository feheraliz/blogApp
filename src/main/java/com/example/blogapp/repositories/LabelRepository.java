package com.example.blogapp.repositories;

import com.example.blogapp.models.Label;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LabelRepository extends CrudRepository<Label, String> {

    Optional<Label> findById(Integer id);

    Optional<Label> deleteById(Integer id);
}
