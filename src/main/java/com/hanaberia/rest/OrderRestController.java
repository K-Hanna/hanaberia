package com.hanaberia.rest;

import com.hanaberia.model.Orders;
import com.hanaberia.model.Users;
import com.hanaberia.service.OrdersService;
import com.hanaberia.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restOrder")
public class OrderRestController {
    
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/user-{id}")
    public Orders createOrder(@PathVariable Long id, @RequestBody Orders orders){
        Users user = usersService.retrieve(id);
        return ordersService.create(orders, user);
    }

    @GetMapping("/{id}")
    public Orders readOrder(@PathVariable Long id){
        return ordersService.retrieve(id);
    }

    @GetMapping
    public List<Orders> readAllOrders(){
        return ordersService.getAllOrders();
    }

    @GetMapping("/user-{id}")
    public List<Orders> getUsersOrders(@PathVariable Long id){
        Users user = usersService.retrieve(id);
        return user.getOrders();
    }

    @PutMapping("/{id}")
    public Orders updateOrder(@PathVariable Long id, @RequestBody Orders order){
        return ordersService.update(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        ordersService.delete(id);
    }
}
