package com.hanaberia.service;

import com.hanaberia.model.Users;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hanaberia.repository.ProductsRepository;
import com.hanaberia.repository.UsersRepository;


@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users get(final Long id) {
        return usersRepository.findById(id).orElseThrow(null);
    }

    public Long create(final Users user) {
        return usersRepository.save(user).getId();
    }

    public void update(final Long id, final Users user) {

        usersRepository.save(user);
    }

    public void delete(final Long id) {
        usersRepository.deleteById(id);
    }
}
