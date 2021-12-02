package com.springapi.springapi.services;

import com.springapi.springapi.model.entities.Product;
import com.springapi.springapi.model.entities.Supplier;
import com.springapi.springapi.model.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SupplierService supplierService;

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

    public void addSupplier(Supplier supplier, Long product_id){
        Product product = findById(product_id);
        if (product == null){
            throw new RuntimeException("Product ID: " + product_id + "not found");
        }
        product.getSuppliers().add(supplier);
        create(product);
    }

    public List<Product> findByNameLike(String name){
        return productRepo.findByNameLike(name);
    }

    public List<Product> findByCategoryId(Long id){
        return productRepo.findByCategory(id);
    }

    // Search by object supplier
    public List<Product> findBySupplierId(Long id){
        Supplier supplier = supplierService.findById(id);
        if (supplier != null){
            return productRepo.findBySupplier(supplier);
        }
        return null;
    }

}
