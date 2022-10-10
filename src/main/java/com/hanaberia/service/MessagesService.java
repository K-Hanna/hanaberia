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
        return messagesRepository.findById(id).orElse(null);
    }
    public void read(Messages message){
        message.setRead(true);
        messagesRepository.save(message);
    }

    public Messages update(Long id, Messages message){

        Messages oldMessage = retrieve(id);

        if(message.getContactForm() != null)
            oldMessage.setContactForm(message.getContactForm());
        if(message.getContact() != null)
            oldMessage.setContact(message.getContact());
        if(message.getTitle() != null)
            oldMessage.setTitle(message.getTitle());
        if(message.getContent() != null)
            oldMessage.setContent(message.getContent());
        if(message.getSentDate() != null)
            oldMessage.setSentDate(message.getSentDate());
        if(message.isRead())
            oldMessage.setRead(message.isRead());

        return messagesRepository.save(oldMessage);

    }

    public void delete(Long id){
        Messages messages = retrieve(id);
        messagesRepository.delete(messages);
    }
}
