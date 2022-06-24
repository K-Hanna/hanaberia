package com.hanaberia.service;

import com.hanaberia.enums.Categories;
import com.hanaberia.model.Products;

import java.util.List;

import com.hanaberia.model.Reservations;
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

    public Long create(final Products product) {
        product.setAvailable(true);

        return productsRepository.save(product).getId();
    }

    public Products retrieve(final Long id) {
        return productsRepository.findById(id).orElseThrow(null);
    }

    public void update(final Long id, final Products newProduct) {

        Products oldProduct = productsRepository.findById(id).orElseThrow(null);
        oldProduct.setImageName(newProduct.getImageName());
        oldProduct.setName(newProduct.getName());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setCategory(newProduct.getCategory());
        oldProduct.setAvailable(true);

        productsRepository.save(oldProduct);
    }

    public void addToCart(Products product, Reservations reservation){
        product.setAvailable(false);
        product.setReservation(reservation);

        productsRepository.save(product);
    }

    public void removeFromCart(Products product){
        product.setAvailable(true);
        product.setReservation(null);

        productsRepository.save(product);
    }

    public void delete(final Long id) {
        productsRepository.deleteById(id);
    }

    public List<Products> getAvailableProducts(){
        return productsRepository.findByAvailableTrue();
    }

    public List<Products> getByCategory(Categories category){
        return productsRepository.findByCategory(category);
    }
}
