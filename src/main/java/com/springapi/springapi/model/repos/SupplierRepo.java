package com.springapi.springapi.model.repos;

import com.springapi.springapi.model.entities.Supplier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierRepo extends CrudRepository<Supplier, Long> {

//    Supplier findByNameOrderByNameAsc(String name);

    Supplier findByEmail(String email);

    List<Supplier> findByNameContains(String name);

//    List<Supplier> findByNameStartsWith(String prefixName);

    List<Supplier> findByNameOrEmailOrderByNameAsc(String name, String email);

}
