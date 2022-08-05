package com.hanaberia.service;

import com.hanaberia.enums.Roles;
import com.hanaberia.model.Orders;
import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hanaberia.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private OrdersService ordersService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users create(final Users user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirm(bCryptPasswordEncoder.encode(user.getConfirm()));
        user.setRoles(Roles.USER);
        return usersRepository.save(user);
    }

    public Users retrieve(final Long id) {
        return usersRepository.findById(id).orElseThrow(null);
    }

    public Users retrieveByName(String name) {
        return usersRepository.findUserByUserName(name);
    }

    public Users retrieveByContact(String contact){
        return usersRepository.findUserByContact(contact);
    }

    public void update(final Long id, final Users user) {

        Users oldUser = usersRepository.findById(id).orElseThrow(null);

        oldUser.setUserName(user.getUserName());
        oldUser.setContact(user.getContact());
        oldUser.setContactForm(user.getContactForm());
        oldUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        oldUser.setConfirm(bCryptPasswordEncoder.encode(user.getConfirm()));
        oldUser.setQuestion(user.getQuestion());
        oldUser.setAnswer(user.getAnswer());

        usersRepository.save(oldUser);
    }

    public void changePassword(final Long id, final Users user){
        Users oldUser = usersRepository.findById(id).orElseThrow(null);
        oldUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        oldUser.setConfirm(bCryptPasswordEncoder.encode(user.getConfirm()));

        usersRepository.save(oldUser);
    }

    public void delete(final Long id) {

        Users user = retrieve(id);
        Reservations reservations = user.getReservations();
        List<Orders> orders = user.getOrders();

        if(reservations != null) {
            reservationsService.delete(reservations.getId());
        }

        if(orders != null) {
            for (Orders order : orders) {
                if (order.isCompleted()) {
                    ordersService.deleteCompletedOrders(order.getId());
                } else {
                    ordersService.delete(order.getId());
                }
            }
        }

        usersRepository.deleteById(id);
    }

    public boolean userNameExists(String userName) {
        return usersRepository.findUserByUserName(userName) != null;
    }

    public boolean contactExists(String contact){
        return usersRepository.findUserByContact(contact) != null;
    }
}
