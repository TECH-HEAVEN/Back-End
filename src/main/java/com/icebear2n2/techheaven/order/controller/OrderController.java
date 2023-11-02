package com.icebear2n2.techheaven.order.controller;
import com.icebear2n2.techheaven.domain.request.OrderRequest;
import com.icebear2n2.techheaven.domain.request.UpdateOrderStatusRequest;
import com.icebear2n2.techheaven.domain.response.OrderResponse;
import com.icebear2n2.techheaven.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        if (orderResponse.isSuccess()) {
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(orderResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/status")
    public ResponseEntity<OrderResponse> changeOrderStatus(@RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        OrderResponse orderResponse = orderService.changeOrderStatus(updateOrderStatusRequest);
        if (orderResponse.isSuccess()) {
            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orderResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
