package com.hanaberia.repository;

import com.hanaberia.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findUserByUserName(String username);
    Users findUserByContact(String contact);
}
