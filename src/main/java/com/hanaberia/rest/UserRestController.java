package com.hanaberia.rest;

import com.hanaberia.model.Users;
import com.hanaberia.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restUser")
public class UserRestController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public void addNewUser(@RequestBody Users user){
        usersService.create(user);
    }

    @GetMapping
    public List<Users> showAllUsers(){
        return usersService.findAll();
    }

    @GetMapping("/{id}")
    public Users showUser(@PathVariable Long id){
        return usersService.retrieve(id);
    }
}
