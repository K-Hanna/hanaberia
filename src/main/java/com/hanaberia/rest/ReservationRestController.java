package com.hanaberia.rest;

import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import com.hanaberia.service.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restReservation")
public class ReservationRestController {

    @Autowired
    private ReservationsService reservationsService;

    @PostMapping
    public Reservations sendNewReservation(@RequestBody Reservations reservation, @RequestBody Users user){
        return reservationsService.create(reservation, user);
    }

    @GetMapping("/{id}")
    public Reservations getReservation(@PathVariable("id") Long id){
        return reservationsService.retrieve(id);
    }

    @GetMapping
    public List<Reservations> showAllReservations(){
        return reservationsService.retrieveAllReservations();
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable("id") Long id){
        reservationsService.delete(id);
    }
}
