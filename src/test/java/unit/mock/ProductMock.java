package unit.mock;

import com.hanaberia.enums.Categories;
import com.hanaberia.model.Products;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductMock {
    
    public static Products mockedProductOne(){
        return Products.builder()
                .id(4L)
                .imageName("one")
                .name("Product One")
                .description("description")
                .price(10)
                .category(Categories.BRACELET)
                .available(true)
                .build();
    }

    public static Products mockedProductTwo(){
        return Products.builder()
                .id(5L)
                .imageName("Two")
                .name("Product Two")
                .description("description")
                .price(10)
                .category(Categories.NECKLACE)
                .available(true)
                .build();
    }

    public static Products mockedProductThree(){
        return Products.builder()
                .id(4L)
                .imageName("Three")
                .name("Product Three")
                .description("description")
                .price(10)
                .category(Categories.EARRINGS)
                .available(true)
                .build();
    }

    public static Set<Products> productSet(){
        Set<Products> productsSet = new HashSet<>();
        productsSet.add(mockedProductOne());
        productsSet.add(mockedProductTwo());
        productsSet.add(mockedProductThree());

        return productsSet;
    }

    public static List<Products> productsList(){
        return Arrays.asList(mockedProductOne(), mockedProductTwo(), mockedProductThree());
    }
}