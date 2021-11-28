package com.springapi.springapi.controllers;

import com.springapi.springapi.model.entities.Product;
import com.springapi.springapi.model.entities.Response;
import com.springapi.springapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("api/v1/product")
public class ProductController implements Serializable {

    @Autowired
    private ProductService productService;

    private Response response = Response.getInstance();

    @PostMapping
    public Response create(@RequestBody Product product){
        Product dataProduct = productService.create(product);
        if (dataProduct != null){
            response.setMessage("Data Berhasil disimpan");
            response.setResponseCode(201);
            response.setData(dataProduct);
        }
        return response;
    }

    @GetMapping
    public Response findAll(){
//        Response<Product> response = new Response<>(
//            "Data Product",
//            200,
//            productService.findAll()
//        );
//        return response;
        response.setMessage("Data Product");
        response.setResponseCode(200);
        response.setData(productService.findAll());
        return response;
    }

    @GetMapping("/{id}")
    public Product findOne (@PathVariable("id") Long id){
        return productService.findById(id);
    }

    @PutMapping
    public Product update (@RequestBody Product product){
        return productService.create(product);
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable("id") Long id) {
        productService.removeById(id);
    }
}
