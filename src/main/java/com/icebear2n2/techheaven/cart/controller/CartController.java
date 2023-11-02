package com.icebear2n2.techheaven.cart.controller;


import com.icebear2n2.techheaven.cart.service.CartService;
import com.icebear2n2.techheaven.domain.request.CartRequest;
import com.icebear2n2.techheaven.domain.response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest cartRequest) {
        CartResponse cartResponse = cartService.createCart(cartRequest);

        if (cartResponse.isSuccess()) {
            return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(cartResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
