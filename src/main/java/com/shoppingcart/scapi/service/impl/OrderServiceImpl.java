package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Order;
import com.shoppingcart.scapi.exception.OrderNotFoundException;
import com.shoppingcart.scapi.repo.OrderRepo;
import com.shoppingcart.scapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    @Override
    public Order getOrder(Long orderId) throws OrderNotFoundException {
        Order order = null;
        try {
            order = orderRepo.findById(orderId).get();
            if (order == null) {
                ResponseCode.ORDER_NOT_FOUND.setReason("Invalid ID or Order ID does not exist in the database.");
                throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
            }
            return order;
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.ORDER_NOT_FOUND.setReason(e.getMessage());
            throw new OrderNotFoundException(ResponseCode.ORDER_NOT_FOUND);
        }
    }
}
