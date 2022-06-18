package com.hanaberia.service;

import com.hanaberia.model.Users;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hanaberia.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public List<Users> findAll() {
        return usersRepository.findAll();
    }


    public Users create(final Users user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirm(bCryptPasswordEncoder.encode(user.getConfirm()));
        return usersRepository.save(user);
    }

    public Users retrieve(final Long id) {
        return usersRepository.findById(id).orElseThrow(null);
    }

    public Users retrieveByName(String name) {
        return usersRepository.findUserByUserName(name);
    }

    public void update(final Long id, final Users user) {

        Users oldUser = usersRepository.findById(id).orElseThrow(null);

        oldUser.setUserName(user.getUserName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        oldUser.setConfirm(bCryptPasswordEncoder.encode(user.getConfirm()));

        usersRepository.save(oldUser);
    }

    public void delete(final Long id) {
        usersRepository.deleteById(id);
    }

}
