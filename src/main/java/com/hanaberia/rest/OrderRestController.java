package com.hanaberia.rest;

import com.hanaberia.model.Orders;
import com.hanaberia.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restOrder")
public class OrderRestController {
    
    @Autowired
    private OrdersService ordersService;

    @PostMapping
    public Orders createNewOrder(@RequestBody Orders order){
        return ordersService.create(order);
    }

    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable("id") Long id){
        return ordersService.retrieve(id);
    }

    @GetMapping
    public List<Orders> showAllOrders(){
        return ordersService.getAllOrders();
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") Long id){
        ordersService.delete(id);
    }
}
