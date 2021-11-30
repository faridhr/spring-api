package com.springapi.springapi.services;

import com.springapi.springapi.model.entities.Product;
import com.springapi.springapi.model.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Product create (Product product) {
        return productRepo.save(product);
    }

    public Product findById(Long id){
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()){
            return product.get();
        }
        return null;
    }

    public Iterable<Product> findAll(){
        return productRepo.findAll();
    }

    public void removeById(Long id){
        productRepo.deleteById(id);
    }

    public List<Product> findByName(String name){
        return productRepo.findByNameContains(name);
    }

}
