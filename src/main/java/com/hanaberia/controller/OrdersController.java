package com.hanaberia.controller;

import com.hanaberia.model.Orders;
import com.hanaberia.model.Products;
import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import com.hanaberia.repository.OrdersRepository;
import com.hanaberia.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ReservationsService reservationsService;

    //retrieve
    @GetMapping("/all")
    public String getAllOrders(Model model){

        List<Reservations> allReservations = reservationsService.retrieveAllReservations();
        for(Reservations reservation : allReservations){
            if(reservation.getExpiringDate().isBefore(LocalDate.now()))
                reservationsService.delete(reservation.getId());
        }

        List<Orders> allOrders = ordersService.getAllOrders();
        allOrders.sort(Comparator.comparing(Orders :: getId).reversed());

        List<String> usersOrders = new ArrayList<>();
        List<Integer> totals = new ArrayList<>();

        if(allOrders.isEmpty()){
            model.addAttribute("message", "Nie ma żadnych zamówień.");
        } else {
            for(Orders order : allOrders){

                String usersName = usersService.retrieve(order.getUser().getId()).getUserName();
                Set<Products> products = order.getProductsSet();
                int total = 0;
                for(Products product : products){
                    total += product.getPrice();
                }

                usersOrders.add(usersName);
                totals.add(total);
            }
        }

        model.addAttribute("orders", allOrders);
        model.addAttribute("totalOrders", allOrders.size() - 1);
        model.addAttribute("users", usersOrders);
        model.addAttribute("totals", totals);

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

            List<Integer> totals = new ArrayList<>();

            model.addAttribute("orders", orders);

            if(orders.size() == 0){
                model.addAttribute("message", "Nie ma żadnych zamówień.");
            }

            for(Orders order : orders){
                Set<Products> products = order.getProductsSet();
                int total = 0;
                for(Products product : products){
                    total += product.getPrice();
                }

                totals.add(total);
            }

            model.addAttribute("count", orders.size() - 1);
            model.addAttribute("total", totals);

            return "order/retrieveOrder";
        }
        return "index";
    }

    //create
    @PostMapping("/add")
    public String orderAdd(@ModelAttribute Orders order,  @SessionAttribute("user") Users user){

        ordersService.create(order, user);

        return "redirect:/orders";
    }

    @GetMapping("/to-complete/{id}")
    public String orderToComplete(@PathVariable Long id, Model model){

        Orders order = ordersService.retrieve(id);
        order.setCompletedDate(LocalDate.now());
        model.addAttribute("orders", order);
        model.addAttribute("startDate", LocalDate.now().minusDays(30));
        model.addAttribute("endDate", LocalDate.now());

        return "order/completeOrder";
    }

    @PostMapping("/complete/{id}")
    public String completeOrder(@PathVariable Long id, @Valid Orders orders){
        ordersService.complete(id, orders);

        return "redirect:/orders/all";
    }

    //update
    @GetMapping("/to-edit/{id}")
    public String orderToEdit(@PathVariable("id") Long id, Model model, @ModelAttribute Orders modelOrder){

        Orders order = ordersService.retrieve(id);

        Set<Products> products = order.getProductsSet();
        int total = 0;
        for(Products product : products){
            total += product.getPrice();
        }

        if(total == 0) {
            model.addAttribute("message", "Lista jest pusta.");
        }

        model.addAttribute("total", total);
        model.addAttribute("orders", order);

        return "order/updateOrder";
    }

    @GetMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable Long id,  Model model){

        Products product = productsService.retrieve(id);
        Orders order = product.getOrder();

        ordersService.changingOrder("remove", id, order.getId());
        model.addAttribute("orders", order);

        return "redirect:/orders/to-edit/" + order.getId();
    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id, Orders orders, Model model){

        Long productId = Long.parseLong(orders.getMessage());
        if(productsService.isProductExist(productId)){
            Products product = productsService.retrieve(productId);
            if(product.isAvailable())
                ordersService.changingOrder("add", productId, id);
        }

        return "redirect:/orders/to-edit/" + id;
    }

    //delete
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
