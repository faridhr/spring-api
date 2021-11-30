package com.springapi.springapi.controllers;

import com.springapi.springapi.model.entities.Product;
import com.springapi.springapi.model.entities.Response;
import com.springapi.springapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Response response;

    @PostMapping
    public ResponseEntity<Response<Product,? extends Object>> create(@Valid @RequestBody Product product, Errors errors){
        if (errors.hasErrors()){
            List<String> errorList = new LinkedList<>();
            errors.getAllErrors().forEach(e -> errorList.add(e.getDefaultMessage()));
            Response<Product, List<String>> response = new Response<>(
                    errorList, HttpStatus.BAD_REQUEST.value(), null
            );
            return ResponseEntity.badRequest().body(response);
        }else {
            Response<Product, String> response = new Response<>(
                    "Data berhasil disimpan", HttpStatus.CREATED.value(), productService.create(product)
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Response<Iterable<Product>, String>> findAll(){
        Iterable<Product> listOfProduct = productService.findAll();
        Response<Iterable<Product>, String> productResponse = new Response<>(
                "Data Product", HttpStatus.OK.value(), listOfProduct
        );
        return ResponseEntity.ok().body(productResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Product, String>> findOne (@PathVariable("id") Long id){
        Product product = productService.findById(id);
        Response<Product, String> response = new Response(
                product != null ? "Data Product" : "Data Kosong", HttpStatus.OK.value(), product
        );
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<Response<Product,? extends Object>> update (@Valid @RequestBody Product product, Errors errors){
        if (errors.hasErrors()){
            List<String> errorList = new LinkedList<>();
            errors.getAllErrors().forEach(e -> errorList.add(e.getDefaultMessage()));
            Response<Product, List<String>> response = new Response<>(
                    errorList, HttpStatus.BAD_REQUEST.value(), null
            );
            return ResponseEntity.badRequest().body(response);
        }else {
            Response<Product, String> response = new Response<>(
                    "Data berhasil diubah", HttpStatus.OK.value(), productService.create(product)
            );
            return ResponseEntity.ok().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Product, String>> destroy(@PathVariable("id") Long id) {
//        productService.removeById(id);
        Response<Product, String > response = new Response<>("Data berhasil dihapus", HttpStatus.OK.value(), null);
        return ResponseEntity.ok().body(response);
    }
}
