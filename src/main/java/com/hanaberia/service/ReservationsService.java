package com.hanaberia.service;

import com.hanaberia.model.Products;
import com.hanaberia.model.Roles;
import com.hanaberia.model.Reservations;
import com.hanaberia.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class ReservationsService {
    
    @Autowired
    private ReservationsRepository reservationsRepository;

    public List<Reservations> findAll() {
        return reservationsRepository.findAll();
    }

    public Reservations create(final Reservations reservation) {

        List<Products> products = reservation.getProductsList();

        for(Products product : products){
            product.setAvailable(false);
        }
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

    public void update(final Long id, final Reservations reservation) {

        Reservations oldReservation = reservationsRepository.findById(id).orElseThrow(null);
        oldReservation.setProductsList(reservation.getProductsList());
        oldReservation.setExpiringDate(LocalDate.now().plusDays(3));

        reservationsRepository.save(oldReservation);
    }

    public void delete(final Long id) {

        Reservations reservation = reservationsRepository.getById(id);
        List<Products> products = reservation.getProductsList();

        for(Products product : products){
            product.setAvailable(true);
        }
        
        reservationsRepository.deleteById(id);
    }
}
