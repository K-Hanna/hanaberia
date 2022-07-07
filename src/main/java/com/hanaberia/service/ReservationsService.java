package com.hanaberia.service;

import com.hanaberia.model.Products;
import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import com.hanaberia.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class ReservationsService {
    
    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private ProductsService productsService;

    public Reservations create(final Reservations reservation, Users user) {

        reservation.setUser(user);
        reservation.setExpiringDate(LocalDate.now().plusDays(7));
        reservation.setProductsSet(new HashSet<>());

        return reservationsRepository.save(reservation);
    }

    public Reservations retrieve(final Long id) {
        Reservations reservation = reservationsRepository.findById(id).orElseThrow(null);

        if(reservation.getExpiringDate().isBefore(LocalDate.now())){
            reservationsRepository.deleteById(id);
            return null;
        } else {
            return reservation;
        }
    }

    public Reservations addToCart(final Long productId, final Users user) {

        Reservations reservation = user.getReservations();

        if(reservation == null){
            reservation = create(new Reservations(), user);
        } else {
            reservation.setExpiringDate(LocalDate.now().plusDays(7));
        }

        Products product = productsService.retrieve(productId);
        productsService.moveProduct(false, product, reservation, null);

        Set<Products> products = reservation.getProductsSet();
        products.add(product);

        return reservationsRepository.save(reservation);
    }

    public void removeFromCart(final Long productId, Long reservationId) {

        Reservations reservation = retrieve(reservationId);
        reservation.setExpiringDate(LocalDate.now().plusDays(7));

        Products product = productsService.retrieve(productId);
        productsService.moveProduct(true, product, null, null);

        Set<Products> products = reservation.getProductsSet();
        products.remove(product);

        reservation.setProductsSet(products);

        reservationsRepository.save(reservation);

    }

    public void delete(final Long id) {

        Reservations reservation = retrieve(id);
        Set<Products> products = reservation.getProductsSet();

        if(products != null) {
            for (Products product : products) {
                productsService.moveProduct(true, product, null, null);
            }
        }

        reservationsRepository.deleteById(id);
    }

    public void deleteEmptyReservation(Reservations reservation){
        reservationsRepository.delete(reservation);
    }
}
