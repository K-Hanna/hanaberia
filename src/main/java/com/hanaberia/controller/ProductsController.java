package com.hanaberia.controller;

import com.hanaberia.enums.Categories;
import com.hanaberia.model.Products;
import com.hanaberia.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    //create
    @PostMapping("/add")
    public String productAdd(@ModelAttribute Products product){

        productsService.create(product);
        return "redirect:/products";
    }

    //retrieve
    @GetMapping()
    public String allProducts(Model model, Products product){

        Map<Categories, List<Products>> productsByCategories = new HashMap<>();

        for(Categories category : Categories.values()){
            List<Products> products = productsService.getByCategory(category);
            productsByCategories.put(category, products);
        }

        model.addAttribute("productsByCategories", productsByCategories);

        return "product/retrieveProducts";
    }

    //update
    @GetMapping("/to-edit/{id}")
    public String productToEdit(@PathVariable("id") Long id, Model model){
        Products product = productsService.read(id);
        model.addAttribute("products", product);

        return "product/updateProduct";
    }

    @PostMapping("/edit/{id}")
    public String productEdit(Model model, @PathVariable("id") Long id, @Valid Products product) {

        model.addAttribute("products", product);
        productsService.update(id, product);

        return "redirect:/products";
    }

    //delete
    @GetMapping("/to-remove/{id}")
    public String productToRemove(@PathVariable("id") Long id, Model model) {

        Products product = productsService.read(id);
        model.addAttribute("products", product);
        return "product/deleteProduct";
    }

    @GetMapping("/remove/{id}")
    public String productRemove(@PathVariable("id") Long id) {

        productsService.delete(id);
        return "redirect:/products";
    }
}
