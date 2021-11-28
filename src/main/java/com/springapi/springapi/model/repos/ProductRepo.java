package com.springapi.springapi.model.repos;

import com.springapi.springapi.model.entities.Product;
import com.springapi.springapi.model.entities.Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {

    List<Product> findByNameContains(String name);

}
