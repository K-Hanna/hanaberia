package com.hanaberia.service;

import com.hanaberia.model.Messages;
import com.hanaberia.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MessagesService{

    @Autowired
    private MessagesRepository messagesRepository;

    public List<Messages> getAllMessages(){
        return messagesRepository.findAll();
    }

    public Messages create(Messages message){

        String convertedContent = message.getContent().replaceAll("[\\t\\n\\r]+", " ");
        message.setSentDate(LocalDate.now());
        message.setContent(convertedContent);
        message.setRead(false);

        return messagesRepository.save(message);
    }

    public Messages retrieve(Long id){
        return messagesRepository.findById(id).orElseThrow(null);
    }
    public Messages read(Messages message){
        message.setRead(true);
        return messagesRepository.save(message);
    }

}
