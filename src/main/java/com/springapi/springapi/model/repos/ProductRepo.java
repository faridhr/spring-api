package com.springapi.springapi.model.repos;

import com.springapi.springapi.model.entities.Product;
import com.springapi.springapi.model.entities.Response;
import com.springapi.springapi.model.entities.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.websocket.server.PathParam;
import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {

    @Query("select p from Product p where p.name=:name")
    List<Product> findByNameContains(@PathParam("name") String name);

    @Query("select p from Product p where p.name like %:name%")
    List<Product> findByNameLike(@PathParam("name") String name);

    @Query("select p from Product p where p.category.id = :id")
    List<Product> findByCategory(@PathParam("id") Long id);

    @Query("select p from Product p where :supplier member of p.suppliers")
    List<Product> findBySupplier(@PathParam("supplier")Supplier supplier);

}
