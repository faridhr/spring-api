package com.springapi.springapi.controllers;

import com.springapi.springapi.configuration.MapperConfiguration;
import com.springapi.springapi.model.dto.SupplierData;
import com.springapi.springapi.model.entities.Response;
import com.springapi.springapi.model.entities.Supplier;
import com.springapi.springapi.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private MapperConfiguration mapper;

    @PostMapping
    public ResponseEntity<Response<Supplier, ? extends Object>> create(@Valid @RequestBody SupplierData supplierData, Errors errors){
        if (errors.hasErrors()){
            List<String> listErrors = new LinkedList<>();
            errors.getAllErrors().forEach(e -> listErrors.add(e.getDefaultMessage()));
            Response<Supplier, List<String>> response = new Response<>(
                    listErrors, HttpStatus.BAD_GATEWAY.value(), null
            );
            return ResponseEntity.badRequest().body(response);
        }

        Supplier supplier = mapper.modelMapper().map(supplierData, Supplier.class);

        Response<Supplier, String> response = new Response<>(
                "Data saved", HttpStatus.CREATED.value(), supplierService.create(supplier)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<Iterable<Supplier>, String>> findAll(){
        Response<Iterable<Supplier>, String> response = new Response<>(
                "Data Supplier", HttpStatus.OK.value(), supplierService.FindAll()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Response<List<Supplier>, String>> findById(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name
    ){
        Response<List<Supplier>, String> response = new Response<>("Data Supplier", HttpStatus.OK.value());
        if (id != null) {
            response.setPayloads(List.of(supplierService.findById(id)));
        } else if(name != null && email != null){
            response.setPayloads(supplierService.findByNameOrEmail(name, email));
        }else if (email != null){
            response.setPayloads(List.of(supplierService.findByEmail(email)));
        }else if (name != null){
            response.setPayloads(supplierService.findByName(name));
        }else {
            response = new Response<>("Invalid parameters", HttpStatus.BAD_REQUEST.value(), null);
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Response<Supplier, ? extends Object>> edit(@Valid @RequestBody SupplierData supplierData, Errors errors){
        if (errors.hasErrors()){
            List<String> listErrors = new LinkedList<>();
            errors.getAllErrors().forEach(e -> listErrors.add(e.getDefaultMessage()));
            Response<Supplier, List<String>> response = new Response<>(
                    listErrors, HttpStatus.BAD_GATEWAY.value(), null
            );
            return ResponseEntity.badRequest().body(response);
        }

        Supplier supplier = mapper.modelMapper().map(supplierData, Supplier.class);

        Response<Supplier, String> response = new Response<>(
                "Data saved", HttpStatus.OK.value(), supplierService.create(supplier)
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Supplier, String>> removeById(@PathVariable("id") Long id){
        Supplier supplier = supplierService.findById(id);
        Response<Supplier, String> response;
        if (supplier == null){
            return ResponseEntity.badRequest().body(
                    response = new Response<>(
                            "Data not found", HttpStatus.BAD_REQUEST.value(), null
                    )
            );
        }
        supplierService.removeById(id);
        return ResponseEntity.ok(
                response = new Response<>(
                        "Data deleted", HttpStatus.OK.value(), null
                )
        );
    }

}
