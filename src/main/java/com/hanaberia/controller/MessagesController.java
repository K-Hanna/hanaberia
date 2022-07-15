package com.hanaberia.controller;

import com.hanaberia.enums.ContactForms;
import com.hanaberia.model.Messages;
import com.hanaberia.service.MessagesService;
import com.hanaberia.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(value = "/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/all")
    public String getAllMessages(Model model){
        List<Messages> messages = messagesService.getAllMessages();
        messages.sort(Comparator.comparing(Messages:: getId).reversed());

        if(messages.isEmpty()){
            model.addAttribute("info", "Nie ma żadnych wiadomości.");
        }

        model.addAttribute("messages", messages);

        return "message/retrieveAllMessages";
    }

    @GetMapping("/to-add")
    public String messageToAdd(@ModelAttribute Messages messages){

        messages.setContactForm(ContactForms.EMAIL);
        return "message/createMessage";
    }

    @PostMapping("/add")
    public String sendMessage(@Valid Messages message, Model model){

        if(validate(model, message) > 0) {
            return "message/createMessage";
        } else {
            messagesService.create(message);
            return "redirect:/";
        }
    }

    @GetMapping("/read/{id}")
    public String readMessage(@PathVariable Long id){

        Messages message = messagesService.retrieve(id);
        messagesService.read(message);

        return "redirect:/messages/all";
    }

    private int validate(Model model, Messages messages){
        Validator validator = new Validator();
        int errors = 0;

        if(messages.getContactForm().equals(ContactForms.EMAIL) && !validator.isEmailValid(messages.getContact())) {
            model.addAttribute("error", "Nieprawidłowy e-mail");
            errors++;
        }

        if(messages.getContactForm().equals(ContactForms.PHONE) && !validator.isPhoneValid(messages.getContact())) {
            model.addAttribute("error", "Nieprawidłowy numer telefonu.");
            errors++;
        }

        return errors;
    }
}
