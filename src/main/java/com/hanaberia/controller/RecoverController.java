package com.hanaberia.controller;

import com.hanaberia.model.Users;
import com.hanaberia.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/recover")
public class RecoverController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public String stepOne(@ModelAttribute Users user){

        return "recover/stepOne";
    }

    @GetMapping("/stepOne")
    public String stepTwo(Model model, Users user){

        Users userFound;
        if (usersService.userNameExists(user.getContact())){
            userFound = usersService.retrieveByName(user.getContact());
            model.addAttribute("users", userFound);

            return "recover/stepTwo";
        } else if (usersService.contactExists(user.getContact())){
            userFound = usersService.retrieveByContact(user.getContact());
            model.addAttribute("users", userFound);

            return "recover/stepTwo";
        } else {
            model.addAttribute("message", "Nie znaleziono użytkownika.");

            return "recover/stepOne";
        }
    }

    @GetMapping("/stepTwo/{id}")
    public String stepThree(Model model, @PathVariable Long id, Users user) {

        Users userFound = usersService.retrieve(id);
        model.addAttribute("users", userFound);

        if (!userFound.getAnswer().equals(user.getAnswer())) {
            model.addAttribute("message", "Podano nieprawidłową odpowiedź.");

            return "recover/stepTwo";
        } else {

            return "recover/stepThree";
        }
    }

    @PostMapping("/stepThree/{id}")
    public String saveData(Model model, @PathVariable("id") Long id, Users user){

        if(!user.getPassword().equals(user.getConfirm())){
            Users userFound = usersService.retrieve(id);
            model.addAttribute("users", userFound);
            model.addAttribute("message", "Hasła nie pasują.");

            return "recover/stepThree";
        } else {
            usersService.changePassword(id, user);

            return "redirect:/users";
        }
    }
}
