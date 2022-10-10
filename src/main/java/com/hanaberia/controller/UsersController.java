package com.hanaberia.controller;

import com.hanaberia.enums.ContactForms;
import com.hanaberia.model.Users;
import com.hanaberia.service.MyUserDetail;
import com.hanaberia.service.UsersService;
import com.hanaberia.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    //create
    @GetMapping("/to-add")
    public String userToAdd(Users user){
        user.setContactForm(ContactForms.EMAIL);
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

    //retrieve
    @GetMapping()
    public String userGet(Model model, HttpSession session) {

        MyUserDetail principal = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Users retrievedUser = usersService.readByName(name);

        session.setAttribute("users", retrievedUser);
        model.addAttribute("users", retrievedUser);

        return "user/retrieveUser";
    }

    //update
    @GetMapping("/to-edit/{id}")
    public String userToEdit(@PathVariable("id") Long id, Model model){

        Users user = usersService.read(id);
        model.addAttribute("users", user);

        return "user/updateUser";
    }

    @PostMapping("/edit/{id}")
    public String userEdit(Model model, @PathVariable("id") Long id, @RequestBody Users user, HttpServletRequest request) {

        Users oldUser = usersService.read(id);
        model.addAttribute("users", oldUser);

        if(validate(model, user, oldUser) > 0){
            return "user/updateUser";
        }

        usersService.update(id, user);

        if(!user.getUserName().equals(oldUser.getUserName())){
            logout(request);
        }

        return "redirect:/users";
    }

    //delete
    @GetMapping("/to-remove/{id}")
    public String userToRemove(@PathVariable("id") Long id, Model model){

        Users user = usersService.read(id);
        model.addAttribute("users", user);

        return "user/deleteUser";
    }

    @GetMapping("/remove/{id}")
    public String userRemove(@PathVariable("id") Long id, HttpServletRequest request) {

        usersService.delete(id);
        logout(request);
        return "redirect:/";
    }

    //validation
    public int validate(Model model, Users newUser, Users oldUser){
        Validator validator = new Validator();
        int errors = 0;
        List<String> messages = new ArrayList<>();

        if(!validator.isUserNameValid(newUser.getUserName())){
           messages.add("Login jest nieodpowiedni.");
            errors++;
        }

        if(usersService.userNameExists(newUser.getUserName()) && !newUser.getUserName().equals(oldUser.getUserName())){
           messages.add("Login jest zajęty.");
            errors++;
        }

        if(newUser.getContactForm().equals(ContactForms.EMAIL)) {
            if (!validator.isEmailValid(newUser.getContact())) {
               messages.add("E-mail jest nieodpowiedni.");
                errors++;
            }
            if (usersService.contactExists(newUser.getContact())
                    && !newUser.getContact().equals(oldUser.getContact())) {
               messages.add("Konto na ten e-mail już istnieje.");
                errors++;
            }
        }

        if(newUser.getContactForm().equals(ContactForms.PHONE)) {
            if (!validator.isPhoneValid(newUser.getContact())) {
               messages.add("Numer telefonu jest nieodpowiedni.");
                errors++;
            }
            if (usersService.contactExists(newUser.getContact())
                    && !newUser.getContact().equals(oldUser.getContact())) {
               messages.add("Konto na ten telefon już istnieje.");
                errors++;
            }
        }

        if(!validator.arePasswordsMatching(newUser.getPassword(), newUser.getConfirm())){
           messages.add("Hasła nie pasują.");
            errors++;
        }

        model.addAttribute("errors", messages);
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
