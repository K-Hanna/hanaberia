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
    public void createUser(@RequestBody Users user){
        usersService.create(user);
    }

    @GetMapping
    public List<Users> readAllUsers(){
        return usersService.findAll();
    }

    @GetMapping("/{id}")
    public Users readUser(@PathVariable Long id){
        return usersService.retrieve(id);
    }

    @PutMapping("/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody Users user){
        return usersService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        usersService.delete(id);
    }
}
