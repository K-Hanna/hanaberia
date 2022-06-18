package com.hanaberia.controller;

import com.hanaberia.model.Users;
import com.hanaberia.repository.UsersRepository;
import com.hanaberia.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;

    @GetMapping("/to-add")
    public String userToAdd(Users user){
        return "user/createUser";
    }

    @PostMapping("/add")
    public String userAdd(@ModelAttribute Users user){

        usersService.create(user);
        return "user/retrieveUser";
    }

    @GetMapping("/{name}")
    public String userGet(@PathVariable("name") String name, Model model){
        Users user = usersService.retrieveByName(name);
        model.addAttribute("users", user);

        return "user/retrieveUser";
    }

    @GetMapping("/to-edit/{id}")
    public String userToEdit(@PathVariable("id") Long id, Model model){

        Users user = usersService.retrieve(id);
        model.addAttribute("users", user);

        return "user/updateUser";
    }

    @PostMapping("/edit/{id}")
    public String UserEdit(Model model, @PathVariable("id") Long id, @Valid Users user) {

        model.addAttribute("users", user);
        usersService.update(id, user);

        return "user/retrieveUser";
    }

    @GetMapping("/to-remove/{id}")
    public String userToDelete(@PathVariable("id") Long id, Model model){

        Users user = usersService.retrieve(id);
        model.addAttribute("users", user);

        return "user/deleteUser";
    }

    @GetMapping("/remove/{id}")
    public String UserRemove(@PathVariable("id") Long id) {

        usersService.delete(id);
        return "redirect:/";
    }
}
