package com.hanaberia.service;

import com.hanaberia.model.Products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hanaberia.repository.ProductsRepository;


@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    public Products get(final Long id) {
        return productsRepository.findById(id).orElseThrow(null);
    }

    public Long create(final Products products) {
        return productsRepository.save(products).getId();
    }

    public void update(final Long id, final Products products) {

        productsRepository.save(products);
    }

    public void delete(final Long id) {
        productsRepository.deleteById(id);
    }

}
