package com.shoppingcart.scapi.service.impl;

import com.shoppingcart.scapi.dto.ResponseCode;
import com.shoppingcart.scapi.entity.Cart;
import com.shoppingcart.scapi.entity.CartItem;
import com.shoppingcart.scapi.exception.CartClearFailedException;
import com.shoppingcart.scapi.exception.CartGetTotalFailedException;
import com.shoppingcart.scapi.exception.CartNotFoundException;
import com.shoppingcart.scapi.exception.CartSaveFailedException;
import com.shoppingcart.scapi.repo.CartItemRepo;
import com.shoppingcart.scapi.repo.CartRepo;
import com.shoppingcart.scapi.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    @Override
    public Cart getCart(Long id) throws CartNotFoundException, CartSaveFailedException {
        Cart cart = null;
        try {
            cart = cartRepo.findById(id).get();
            if (cart == null) {
                ResponseCode.CART_NOT_FOUND.setReason("Invalid ID or Cart ID does not exist in the database.");
                throw new CartNotFoundException(ResponseCode.CART_NOT_FOUND);
            }

            BigDecimal totalAmount = cart.getTotalAmount();
            cart.setTotalAmount(totalAmount);
            return cartRepo.save(cart);
        } catch (CartNotFoundException e) {
            throw new CartNotFoundException(ResponseCode.CART_NOT_FOUND);
        } catch (Exception e) {
            ResponseCode.CART_SAVE_FAIL.setReason(e.getMessage());
            throw new CartSaveFailedException(ResponseCode.CART_SAVE_FAIL);
        }
    }

    @Override
    public void clearCart(Long id) throws CartClearFailedException {
        Cart cart = null;
        try {
            cart = getCart(id);
            cartItemRepo.deleteAllByCartId(id);
            cart.getItems().clear();
            cartRepo.deleteById(id);
        } catch (Exception e) {
            ResponseCode.CART_CLEAR_FAIL.setReason(e.getMessage());
            throw new CartClearFailedException(ResponseCode.CART_CLEAR_FAIL);
        }
    }

    @Override
    public BigDecimal getTotalPrice(Long id) throws CartGetTotalFailedException {
        try {
            Cart cart = getCart(id);
            return cart.getTotalAmount();
        } catch (Exception e) {
            ResponseCode.CART_GET_TOTAL_FAIL.setReason(e.getMessage());
            throw new CartGetTotalFailedException(ResponseCode.CART_GET_TOTAL_FAIL);
        }

    }
}
