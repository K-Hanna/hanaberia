package com.hanaberia.controller;

import com.hanaberia.enums.Categories;
import com.hanaberia.model.Products;
import com.hanaberia.repository.ProductsRepository;
import com.hanaberia.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/products")
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsService productsService;

    @GetMapping()
    public String allProducts(Model model){

        List<Products> bracelets = productsService.getByCategory(Categories.BRACELET);
        model.addAttribute("bracelets", bracelets);

        List<Products> earrings = productsService.getByCategory(Categories.EARRINGS);
        model.addAttribute("earrings", earrings);

        List<Products> necklaces = productsService.getByCategory(Categories.NECKLACE);
        model.addAttribute("necklaces", necklaces);

        List<Products> rings = productsService.getByCategory(Categories.RING);
        model.addAttribute("rings", rings);

        return "product/retrieveProducts";
    }

    @GetMapping("/to-add")
    public String productToAdd(Products product){
        return "product/createProduct";
    }

    @PostMapping("/add")
    public String productAdd(@ModelAttribute Products product){

        productsService.create(product);
        return "redirect:/products";
    }

    @GetMapping("/to-edit/{id}")
    public String productToEdit(@PathVariable("id") Long id, Model model){
        Products product = productsService.retrieve(id);
        model.addAttribute("products", product);

        return "product/updateProduct";
    }

    @PostMapping("/edit/{id}")
    public String productEdit(Model model, @PathVariable("id") Long id, @Valid Products product) {

        model.addAttribute("products", product);
        productsService.update(id, product);

        return "redirect:/products";
    }

    @GetMapping("/to-remove/{id}")
    public String productToRemove(@PathVariable("id") Long id, Model model) {

        Products product = productsService.retrieve(id);
        model.addAttribute("products", product);
        return "product/deleteProduct";
    }

    @GetMapping("/remove/{id}")
    public String productRemove(@PathVariable("id") Long id) {

        productsService.delete(id);
        return "redirect:/products";
    }
}
