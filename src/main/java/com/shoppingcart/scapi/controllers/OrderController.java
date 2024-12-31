package com.shoppingcart.scapi.controllers;

import com.shoppingcart.scapi.dto.APIResponseDto;
import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Order;
import com.shoppingcart.scapi.exception.OrderNotFoundException;
import com.shoppingcart.scapi.exception.PlaceOrderFailedException;
import com.shoppingcart.scapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/{userId}")
    public ResponseEntity<APIResponseDto> createOrder(@RequestParam Long userId) {
        Order order = null;
        try {
            order = orderService.placeOrder(userId);
        } catch (PlaceOrderFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Order created successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, order));
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<APIResponseDto> getOrderById(@PathVariable Long orderId) {
        Order order = null;
        try {
            order = orderService.getOrder(orderId);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResponseDto.getInstance(e.getResponseCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseCode.SUCCESS.setReason("Order retrieved successfully!");
        return ResponseEntity.ok(APIResponseDto.getInstance(ResponseCode.SUCCESS, order));
    }


}
