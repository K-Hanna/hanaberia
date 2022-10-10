package com.hanaberia.rest;

import com.hanaberia.enums.Categories;
import com.hanaberia.model.Products;
import com.hanaberia.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restProduct")
public class ProductsRestController {

    @Autowired
    private ProductsService productsService;

    @PostMapping
    public void createProduct(@RequestBody Products product){
        productsService.create(product);
    }

    @GetMapping
    public List<Products> showAllProducts(){
        return productsService.getAllProducts();
    }

    @GetMapping("/show/{category}")
    public List<Products> showByCategory(@PathVariable Categories category){
        return productsService.getByCategory(category);
    }

    @GetMapping("/{id}")
    public Products readProduct(@PathVariable Long id){
        return productsService.retrieve(id);
    }

    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable Long id, @RequestBody Products product){
        return productsService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productsService.delete(id);
    }
}
