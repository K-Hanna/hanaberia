package com.hanaberia.controller;

import com.hanaberia.model.Orders;
import com.hanaberia.model.Products;
import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import com.hanaberia.repository.OrdersRepository;
import com.hanaberia.service.*;
import com.hanaberia.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/orders")
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ReservationsService reservationsService;

    @GetMapping("/all")
    public String getAllOrders(Model model){

        List<Orders> allOrders = ordersService.getAllOrders();
        allOrders.sort(Comparator.comparing(Orders :: getId).reversed());
        model.addAttribute("orders", allOrders);

        if(allOrders.isEmpty()){
            model.addAttribute("message", "Nie ma żadnych zamówień.");
        } else {
            for(Orders order :allOrders){

                String usersName = usersService.retrieve(order.getUser().getId()).getUserName();
                Set<Products> products = order.getProductsSet();
                int total = 0;
                for(Products product : products){
                    total += product.getPrice();
                }

                model.addAttribute("name", usersName);
                model.addAttribute("total", total);
            }
        }

        return "order/retrieveAllOrders";
    }

    @GetMapping
    public String getUsersOrders(Model model, HttpSession session) {

        MyUserDetail principal = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Users user = usersService.retrieveByName(name);

        if (user != null) {
            session.setAttribute("user", user);

            List<Orders> orders = user.getOrders();
            orders.sort(Comparator.comparing(Orders :: getId).reversed());

            model.addAttribute("orders", orders);

            if(orders.isEmpty()){
                model.addAttribute("message", "Nie ma żadnych zamówień.");
            }

            for(Orders order : orders){
                Set<Products> products = order.getProductsSet();
                int total = 0;
                for(Products product : products){
                    total += product.getPrice();
                }
                model.addAttribute("total", total);
            }

            return "order/retrieveOrder";
        }
        return "index";
    }

    @GetMapping("/to-add")
    public String orderToAdd(@SessionAttribute("user") Users user, Model model, @ModelAttribute Orders order){

        Reservations reservation = user.getReservations();
        Set<Products> products = reservation.getProductsSet();
        int total = 0;
        for(Products product : products){
            total += product.getPrice();
        }

        model.addAttribute("total", total);

        return "order/createOrder";
    }

    @PostMapping("/add")
    public String orderAdd(@ModelAttribute Orders order,  @SessionAttribute("user") Users user){

        order.setUser(user);
        ordersService.create(order);
        List<Orders> usersOrders = user.getOrders();
        usersOrders.add(order);
        user.setOrders(usersOrders);

        Reservations reservation = user.getReservations();
        Set<Products> products = reservation.getProductsSet();
        for(Products product : products){
            productsService.moveProduct(false, product, null, order);
        }
        order.setProductsSet(products);
        reservationsService.deleteEmptyReservation(reservation);

        return "redirect:/orders";
    }

    @GetMapping("/to-complete/{id}")
    public String orderToComplete(@PathVariable Long id, Model model){

        Orders order = ordersService.retrieve(id);
        order.setCompletedDate(LocalDate.now());
        model.addAttribute("orders", order);

        return "order/completeOrder";
    }

    @PostMapping("/complete/{id}")
    public String completeOrder(@PathVariable Long id, @Valid Orders orders){
        ordersService.complete(id, orders);

        return "redirect:/orders/all";
    }

    @GetMapping("/to-edit/{id}")
    public String orderToEdit(@PathVariable("id") Long id, Model model){

        Orders order = ordersService.retrieve(id);

        Set<Products> products = order.getProductsSet();
        int total = 0;
        for(Products product : products){
            total += product.getPrice();
        }

        model.addAttribute("total", total);
        model.addAttribute("orders", order);

        return "order/updateOrder";
    }

    @PostMapping("/edit/{id}")
    public String orderEdit(Model model, @PathVariable("id") Long id, @Valid Orders order) {

/*        model.addAttribute("orders", order);
        ordersService.update(id, order);*/

        return "redirect:/orders/all";
    }

    @GetMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable Long id,  @SessionAttribute Orders order, Model model){

        System.out.println(order.getId());
        Long orderId = order.getId();
        ordersService.changingOrder("remove", id, orderId);
        model.addAttribute("orders", order);

        return "order/updateOrder";
    }

    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id,  @Valid Orders order, Model model){

        Long orderId = order.getId();
        ordersService.changingOrder("add", id, orderId);
        model.addAttribute("orders", order);

        return "order/updateOrder";
    }

    @GetMapping("/to-remove/{id}")
    public String orderToRemove(@PathVariable("id") Long id, Model model) {

        Orders order = ordersService.retrieve(id);
        model.addAttribute("orders", order);
        return "order/deleteOrder";
    }

    @GetMapping("/remove/{id}")
    public String orderRemove(@PathVariable("id") Long id) {

        ordersService.delete(id);
        return "redirect:/orders/all";
    }
}
