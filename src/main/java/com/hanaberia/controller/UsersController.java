package com.hanaberia.controller;

import com.hanaberia.model.Users;
import com.hanaberia.repository.UsersRepository;
import com.hanaberia.service.UsersService;
import com.hanaberia.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
    public String userAdd(@Valid Users user, Model model){

        if(validate(model, user, new Users()) > 0){
            return "user/createUser";
        } else {
            usersService.create(user);
            return "redirect:/";
        }
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
    public String UserEdit(Model model, @PathVariable("id") Long id, @Valid Users user, HttpServletRequest request) {

        Users oldUser = usersService.retrieve(id);
        model.addAttribute("users", oldUser);

        if(validate(model, user, oldUser) > 0){
            return "user/updateUser";
        }

        usersService.update(id, user);

        if(!user.getUserName().equals(oldUser.getUserName())){
            logout(request);
        }

        return "user/retrieveUser";
    }

    @GetMapping("/to-remove/{id}")
    public String userToDelete(@PathVariable("id") Long id, Model model){

        Users user = usersService.retrieve(id);
        model.addAttribute("users", user);

        return "user/deleteUser";
    }

    @GetMapping("/remove/{id}")
    public String UserRemove(@PathVariable("id") Long id, HttpServletRequest request) {

        usersService.delete(id);
        logout(request);
        return "redirect:/";
    }

    public int validate(Model model, Users newUser, Users oldUser){
        Validator validator = new Validator();
        int errors = 0;

        if(!validator.isUserNameValid(newUser.getUserName())){
            model.addAttribute("errorUN1", "Login jest nieodpowiedni.");
            errors++;
        }

        if(usersService.userNameExists(newUser.getUserName()) && !newUser.getUserName().equals(oldUser.getUserName())){
            model.addAttribute("errorUN2", "Login jest zajęty.");
            errors++;
        }

        if(!newUser.getEmail().isEmpty()) {
            if (!validator.isEmailValid(newUser.getEmail())) {
                model.addAttribute("errorE1", "E-mail jest nieodpowiedni.");
                errors++;
            }
            if (usersService.emailExists(newUser.getEmail()) && !newUser.getEmail().equals(oldUser.getEmail())) {
                model.addAttribute("errorE2", "Konto na ten e-mail już istnieje.");
                errors++;
            }
        }

        if(!validator.isPhoneValid(newUser.getPhone()) && !newUser.getPhone().isEmpty()){
            model.addAttribute("errorNo", "Numer telefonu jest nieodpowiedni.");
            errors++;
        }

        if(newUser.getEmail().isEmpty() && newUser.getPhone().isEmpty()){
            model.addAttribute("errorEN", "Podaj e-mail albo numer telefonu.");
            errors++;
        }

        if(!validator.arePasswordsMatching(newUser.getPassword(), newUser.getConfirm())){
            model.addAttribute("errorP", "Hasła nie pasują.");
            errors++;
        }

        return errors;
    }

    public void logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            System.out.println(e);
        }
    }
}
