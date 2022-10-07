package com.hanaberia.rest;

import com.hanaberia.model.Messages;
import com.hanaberia.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restMessage")
public class MessageRestController {

    @Autowired
    private MessagesService messagesService;

    @PostMapping
    public Messages createMessage(@RequestBody Messages message){
        return messagesService.create(message);
    }

    @GetMapping("/{id}")
    public Messages readMessage(@PathVariable("id") Long id){
        return messagesService.retrieve(id);
    }

    @GetMapping
    public List<Messages> readAllMessages(){
        return messagesService.getAllMessages();
    }

    @PutMapping("/{id}")
    public Messages updateMessage(@PathVariable("id") Long id, @RequestBody Messages newMessage){
        return messagesService.update(id, newMessage);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable("id") Long id){
        messagesService.delete(id);
    }
}
