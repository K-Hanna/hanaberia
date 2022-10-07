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
    public void addNewProduct(@RequestBody Products product){
        productsService.create(product);
    }

    @GetMapping
    public List<Products> showAllProducts(){
        return productsService.getAllProducts();
    }

    @GetMapping("/bracelet")
    public List<Products> showBracelets(){
        List<Products> bracelets = productsService.getByCategory(Categories.BRACELET);
        System.out.println(bracelets.size());

        return bracelets;
    }

    @GetMapping("/{id}")
    public Products showProduct(@PathVariable Long id){
        return productsService.retrieve(id);
    }
}
