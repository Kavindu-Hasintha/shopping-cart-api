package com.shoppingcart.scapi.repo;

import com.shoppingcart.scapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
