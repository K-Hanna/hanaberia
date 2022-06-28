package com.hanaberia.controller;

import com.hanaberia.model.Products;
import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import com.hanaberia.repository.ReservationsRepository;
import com.hanaberia.service.MyUserDetail;
import com.hanaberia.service.ProductsService;
import com.hanaberia.service.ReservationsService;
import com.hanaberia.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProductsService productsService;

    @GetMapping()
    public String getUsersReservation(Model model, HttpSession session) {

        MyUserDetail principal = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Users user = usersService.retrieveByName(name);

        if (user != null) {
            session.setAttribute("user", user);

            Reservations reservation = user.getReservations();

            if(reservation == null){
                reservation = new Reservations();
            }
            model.addAttribute("reservations", reservation);

            Set<Products> products = reservation.getProductsSet();
            int total = 0;
            for(Products product : products){
                total += product.getPrice();
            }

            model.addAttribute("total", total);

            if(total == 0) {
                model.addAttribute("message", "Koszyk jest pusty.");
            }

            return "reservation/retrieveReservation";
        }
        return "index";
    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session, Model model){

        MyUserDetail principal = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Users user = usersService.retrieveByName(name);

        session.setAttribute("user", user);
        reservationsService.addToCart(id, user);

        return "redirect:/";
    }

    @GetMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable Long id, @SessionAttribute Users user, Model model){

        reservationsService.removeFromCart(id, user.getReservations().getId());

        Reservations reservation = user.getReservations();
        model.addAttribute("reservations", reservation);

        return "redirect:/reservations";
    }

    @GetMapping("/to-remove/{id}")
    public String reservationToRemove(@PathVariable("id") Long id, Model model){

        Reservations reservation = reservationsService.retrieve(id);
        model.addAttribute("reservations", reservation);

        return "reservation/deleteReservation";
    }

    @GetMapping("/remove/{id}")
    public String reservationRemove(@PathVariable("id") Long id) {

        reservationsService.delete(id);
        return "redirect:/reservations";
    }
}
