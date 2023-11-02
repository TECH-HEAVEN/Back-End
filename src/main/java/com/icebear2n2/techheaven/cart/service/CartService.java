package com.icebear2n2.techheaven.cart.service;

import com.icebear2n2.techheaven.domain.entity.Cart;
import com.icebear2n2.techheaven.domain.entity.User;
import com.icebear2n2.techheaven.domain.repository.CartRepository;
import com.icebear2n2.techheaven.domain.repository.UserRepository;
import com.icebear2n2.techheaven.domain.request.CartRequest;
import com.icebear2n2.techheaven.domain.response.CartResponse;
import com.icebear2n2.techheaven.exception.ErrorCode;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    //        TODO: CREATE

    public CartResponse createCart(CartRequest cartRequest) {
        User user = userRepository.findById(cartRequest.getUserId())
                .orElseThrow(() -> new TechHeavenException(ErrorCode.USER_NOT_FOUND));

        try {
            Cart cart = cartRequest.toEntity(user);
            Cart saveCart = cartRepository.save(cart);
            return CartResponse.success(saveCart);
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            return CartResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }
}
