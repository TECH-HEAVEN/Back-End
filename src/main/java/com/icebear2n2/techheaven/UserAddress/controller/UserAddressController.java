package com.icebear2n2.techheaven.UserAddress.controller;

import com.icebear2n2.techheaven.UserAddress.service.UserAddressService;
import com.icebear2n2.techheaven.domain.request.UpdateUserAddressRequest;
import com.icebear2n2.techheaven.domain.request.UserAddressRequest;
import com.icebear2n2.techheaven.domain.response.UserAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class UserAddressController {
    private final UserAddressService userAddressService;

    @PostMapping
    public ResponseEntity<UserAddressResponse> createUserAddressInfo(@RequestBody UserAddressRequest userAddressRequest) {
        UserAddressResponse userAddressResponse = userAddressService.createUserAddressInfo(userAddressRequest);

        if (userAddressResponse.isSuccess()) {
            return new ResponseEntity<>(userAddressResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(userAddressResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserAddressResponse> updateUserAddressInfo(@RequestBody UpdateUserAddressRequest updateUserAddressRequest) {
        UserAddressResponse userAddressResponse = userAddressService.updateUserAddressInfo(updateUserAddressRequest);

        if (userAddressResponse.isSuccess()) {
            return new ResponseEntity<>(userAddressResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userAddressResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeUserAddressInfo(@RequestBody Long userAddressId) {
        userAddressService.removeUserAddressInfo(userAddressId);
        return new ResponseEntity<>("User address information remove was successful.", HttpStatus.OK);
    }
}
