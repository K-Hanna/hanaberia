package com.hanaberia;

import com.hanaberia.model.Categories;
import com.hanaberia.model.Products;
import com.hanaberia.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/")
    public String index(Model model) {

        List<Products> bracelets = productsService.getByCategory(Categories.BRACELET);
        model.addAttribute("bracelets", bracelets);

        List<Products> earrings = productsService.getByCategory(Categories.EARRINGS);
        model.addAttribute("earrings", earrings);

        List<Products> necklaces = productsService.getByCategory(Categories.NECKLACE);
        model.addAttribute("necklaces", necklaces);

        List<Products> rings = productsService.getByCategory(Categories.RING);
        model.addAttribute("rings", rings);

        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
