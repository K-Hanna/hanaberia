package com.hanaberia.repository;

import com.hanaberia.model.Orders;
import com.hanaberia.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
