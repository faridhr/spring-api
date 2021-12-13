package com.springapi.springapi.controllers;

import com.springapi.springapi.configuration.MapperConfiguration;
import com.springapi.springapi.model.dto.CategoryData;
import com.springapi.springapi.model.entities.Category;
import com.springapi.springapi.model.entities.Response;
import com.springapi.springapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MapperConfiguration mapper;

    @PostMapping
    public ResponseEntity<Response<Category, ? extends Object>> create(@Valid @RequestBody CategoryData categoryData, Errors errors){
        if (errors.hasErrors()){
            List<String> listErrors = new LinkedList<>();
            errors.getAllErrors().forEach(e -> listErrors.add(e.getDefaultMessage()));
            Response<Category, List<String>> response = new Response<>(
                    listErrors, HttpStatus.BAD_GATEWAY.value(), null
            );
            return ResponseEntity.badRequest().body(response);
        }

        Category category = mapper.modelMapper().map(categoryData, Category.class);

        Response<Category, String> response = new Response<>(
                "Data saved", HttpStatus.CREATED.value(), categoryService.create(category)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<Iterable<Category>, String>> findAll(){
        Response<Iterable<Category>, String> response = new Response<>(
                "Data Category", HttpStatus.OK.value(), categoryService.findAll()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Response<Iterable<Category>, String>> findById(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name
    ){
        Response<Iterable<Category>, String> response = new Response<>("Data Category", HttpStatus.OK.value());
        if (id != null){
            response.setPayloads(List.of(categoryService.findById(id)));
        } else if (name != null) {
            Pageable pageable;
            if (sort.equalsIgnoreCase("desc")){
                pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 3, Sort.by("name").descending());
            }else {
                pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 3);
            }
            response.setPayloads(categoryService.findByName(name, pageable));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Response<Category, ? extends Object>> edit(@Valid @RequestBody CategoryData categoryData, Errors errors){
        if (errors.hasErrors()){
            List<String> listErrors = new LinkedList<>();
            errors.getAllErrors().forEach(e -> listErrors.add(e.getDefaultMessage()));
            Response<Category, List<String>> response = new Response<>(
                    listErrors, HttpStatus.BAD_GATEWAY.value(), null
            );
            return ResponseEntity.badRequest().body(response);
        }

        Category category = mapper.modelMapper().map(categoryData, Category.class);

        Response<Category, String> response = new Response<>(
                "Data saved", HttpStatus.OK.value(), categoryService.create(category)
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Category, String>> removeById(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);
        Response<Category, String> response;
        if (category == null){
            return ResponseEntity.badRequest().body(
                    response = new Response<>(
                            "Data not found", HttpStatus.BAD_REQUEST.value(), null
                    )
            );
        }
        categoryService.removeById(id);
        return ResponseEntity.ok(
            response = new Response<>(
                    "Data deleted", HttpStatus.OK.value(), null
            )
        );
    }
}
