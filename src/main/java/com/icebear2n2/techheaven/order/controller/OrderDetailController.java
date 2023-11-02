package com.icebear2n2.techheaven.order.controller;

import com.icebear2n2.techheaven.domain.request.OrderDetailRequest;
import com.icebear2n2.techheaven.domain.request.UpdateOrderDetailItemQuantityRequest;
import com.icebear2n2.techheaven.domain.response.OrderDetailResponse;
import com.icebear2n2.techheaven.order.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/details")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<OrderDetailResponse> createOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest) {
        OrderDetailResponse orderDetailResponse = orderDetailService.createOrderDetail(orderDetailRequest);
        if (orderDetailResponse.isSuccess()) {
            return new ResponseEntity<>(orderDetailResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(orderDetailResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<OrderDetailResponse> updateOrderDetailItemQuantity(@RequestBody UpdateOrderDetailItemQuantityRequest updateOrderDetailItemQuantityRequest) {
        OrderDetailResponse orderDetailResponse = orderDetailService.updateOrderDetailItemQuantity(updateOrderDetailItemQuantityRequest);
        if (orderDetailResponse.isSuccess()) {
            return new ResponseEntity<>(orderDetailResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orderDetailResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
