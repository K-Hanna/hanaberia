package com.hanaberia.repository;

import com.hanaberia.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Products, Long> {
}
