package com.hanaberia;

import com.hanaberia.enums.Categories;
import com.hanaberia.model.Products;
import com.hanaberia.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/")
    public String index(Model model) {

        Map<Categories, List<Products>> productsByCategories = new HashMap<>();

        for(Categories category : Categories.values()){
            List<Products> products = productsService.getByCategory(category);
            products.sort(Comparator.comparing(Products::getId).reversed());
            productsByCategories.put(category, products);
        }

        model.addAttribute("productsByCategories", productsByCategories);

        return "index";
    }

    @GetMapping("/archive")
    public String getArchive(Model model){

        List<Products> archiveProducts = productsService.getUnavailableProducts();
        model.addAttribute("products", archiveProducts);

        return "archive";
    }

    @RequestMapping("/login")
    public String login(){

        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/logout-success")
    public String showLogoutPage() {
        return "logout-success";
    }

    @GetMapping("/403")
    public String noAccess(){
        return "noAccess";
    }

}
