package com.springapi.springapi.services;

import com.springapi.springapi.model.entities.Supplier;
import com.springapi.springapi.model.repos.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {

    @Autowired
    private SupplierRepo supplierRepo;

    public Supplier create(Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    public Iterable<Supplier> FindAll(){
        return supplierRepo.findAll();
    }

    public Supplier findById(Long id){
        Optional<Supplier> supplier = supplierRepo.findById(id);
        return supplier.orElse(null);
    }

    public List<Supplier> findByName(String name){
        return supplierRepo.findByNameContains(name);
    }

    public  List<Supplier> findByNameOrEmail(String name, String email){
        return supplierRepo.findByNameOrEmailOrderByNameAsc(name, email);
    }

    public Supplier findByEmail(String email){
        return supplierRepo.findByEmail(email);
    }

    public void removeById(Long id){
        supplierRepo.deleteById(id);
    }

}
