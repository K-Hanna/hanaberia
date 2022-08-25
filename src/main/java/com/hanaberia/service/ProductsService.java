package com.hanaberia.service;

import com.hanaberia.enums.Categories;
import com.hanaberia.model.Orders;
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

    public Products create(final Products product) {
        String covertDesc = product.getDescription().replaceAll("[\\t\\n\\r]+", " ");
        product.setDescription(covertDesc);
        product.setAvailable(true);

        return productsRepository.save(product);
    }

    public Products retrieve(final Long id) {
        return productsRepository.findById(id).orElseThrow(null);
    }

    public void update(final Long id, final Products newProduct) {

        Products oldProduct = productsRepository.findById(id).orElseThrow(null);
        String covertDesc = newProduct.getDescription().replaceAll("[\\t\\n\\r]+", " ");
        oldProduct.setImageName(newProduct.getImageName());
        oldProduct.setName(newProduct.getName());
        oldProduct.setDescription(covertDesc);
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setCategory(newProduct.getCategory());
        oldProduct.setAvailable(true);

        productsRepository.save(oldProduct);
    }

    public void moveProduct(boolean available, Products product, Reservations reservation, Orders order){
        product.setAvailable(available);
        product.setReservation(reservation);
        product.setOrder(order);

        productsRepository.save(product);
    }

    public void delete(final Long id) {
        productsRepository.deleteById(id);
    }

    public List<Products> getUnavailableProducts(){
        return productsRepository.findByAvailableFalse();
    }

    public List<Products> getByCategory(Categories category){
        return productsRepository.findByCategory(category);
    }

    public boolean isProductExist(Long id){
        return productsRepository.existsById(id);
    }
}
