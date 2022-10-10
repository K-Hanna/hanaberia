package com.hanaberia.rest;

import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import com.hanaberia.service.ReservationsService;
import com.hanaberia.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restReservation")
public class ReservationRestController {

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/user-{id}")
    public Reservations createReservation(@RequestBody Reservations reservation, @PathVariable Long id){
        Users user = usersService.retrieve(id);
        return reservationsService.create(reservation, user);
    }

    @GetMapping("/{id}")
    public Reservations readReservation(@PathVariable Long id){
        return reservationsService.retrieve(id);
    }

    @GetMapping
    public List<Reservations> readAllReservations(){
        return reservationsService.retrieveAllReservations();
    }

    @GetMapping("/user-{id}")
    public Reservations getUsersReservation(@PathVariable Long id){
        Users user = usersService.retrieve(id);
        return user.getReservations();
    }

    @PutMapping("/{id}")
    public Reservations updateReservation(@PathVariable Long id, @RequestBody Reservations reservation){
        return reservationsService.update(id, reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id){
        reservationsService.delete(id);
    }
}
