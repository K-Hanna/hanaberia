package com.hanaberia.service;

import com.hanaberia.model.Orders;
import com.hanaberia.model.Products;
import com.hanaberia.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ReservationsService reservationsService;

    public List<Orders> getAllOrders(){
        return ordersRepository.findAll();
    }

    public Orders create(final Orders order) {

        String message = order.getMessage().replaceAll("[\\t\\n\\r]+", " ");
        order.setMessage(message);

        return ordersRepository.save(order);
    }

    public Orders retrieve(final Long id) {
        return ordersRepository.findById(id).orElseThrow(null);
    }

    public void complete(final Long id, Orders orders) {

        Orders order = retrieve(id);
        order.setCompleted(true);
        order.setCompletedDate(orders.getCompletedDate());

        ordersRepository.save(order);
    }

    public void delete(final Long id) {
        Orders order = retrieve(id);
        Set<Products> products = order.getProductsSet();

        if(products.size() > 0) {
            for (Products product : products) {
                productsService.moveProduct(true, product, null, null);
            }
        }

        ordersRepository.deleteById(id);
    }

    public void deleteCompletedOrders(final Long id){
        Orders order = retrieve(id);
        Set<Products> products = order.getProductsSet();

        for(Products product : products){
            productsService.moveProduct(false, product, null, null);
        }

        ordersRepository.deleteById(id);
    }

    public void changingOrder(String direction, Long productId, Long orderId) {

        Products product = productsService.retrieve(productId);
        Orders order = retrieve(orderId);
        Set<Products> products = order.getProductsSet();

        switch (direction){
            case "add":
                productsService.moveProduct(false, product, null, order);
                products.remove(product);
                break;
            case "remove":
                productsService.moveProduct(true, product, null, null);
                products.add(product);
                break;
        }

        order.setProductsSet(products);

        ordersRepository.save(order);
    }

    public Orders update(Long id, Orders order) {
        Orders oldOrder = retrieve(id);

        String message = order.getMessage().replaceAll("[\\t\\n\\r]+", " ");
        oldOrder.setMessage(message);

        if(order.getCompletedDate() != null){
            oldOrder.setCompletedDate(order.getCompletedDate());
            oldOrder.setCompleted(true);
        }

        return ordersRepository.save(oldOrder);
    }
}
