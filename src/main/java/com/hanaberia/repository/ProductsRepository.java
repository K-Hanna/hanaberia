package com.hanaberia.repository;

import com.hanaberia.model.Categories;
import com.hanaberia.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Products, Long> {
    List<Products> findByAvailableTrue();
    List<Products> findByCategory(Categories category);
}
