package com.hanaberia.service;

import com.hanaberia.model.Orders;
import com.hanaberia.model.Products;
import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import com.hanaberia.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        for(Products product : products){
            productsService.moveProduct(true, product, null, null);
        }

        ordersRepository.deleteById(id);
    }

    public void changingOrder(String direction, Long productId, Long orderId) {

        Products product = productsService.retrieve(productId);
        Orders oldOrder = retrieve(orderId);

        switch (direction){
            case "add":
                productsService.moveProduct(false, product, null, oldOrder);
                break;
            case "remove":
                productsService.moveProduct(true, product, null, null);
                break;
        }

        Set<Products> products = oldOrder.getProductsSet();
        products.remove(product);

        oldOrder.setProductsSet(products);

        ordersRepository.save(oldOrder);
    }
}
