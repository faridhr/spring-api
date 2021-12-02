package com.springapi.springapi.services;

import com.springapi.springapi.model.entities.Category;
import com.springapi.springapi.model.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category create(Category category){
        return categoryRepo.save(category);
    }

    public Iterable<Category> findAll(){
        return categoryRepo.findAll();
    }

    public Category findById(Long id){
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()){
            return category.get();
        }
        return null;
    }

    public Iterable<Category> findByName(String name, Pageable pageable){
        return categoryRepo.findByNameContains(name, pageable);
    }

    public void removeById(Long id){
        categoryRepo.deleteById(id);
    }
}
