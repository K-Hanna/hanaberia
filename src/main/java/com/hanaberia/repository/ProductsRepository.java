package com.hanaberia.repository;

import com.hanaberia.enums.Categories;
import com.hanaberia.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    List<Products> findByAvailableFalse();
    List<Products> findByCategory(Categories category);
}
