package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.UserAddress;
import com.icebear2n2.techheaven.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressRequest {
    private Long userId;
    private String receiverName;
    private String receiverPhone;

    private String postCode;
    private String address;
    private String addressDetail;
    private String addressExtra;


    public UserAddress toEntity(User user) {
        return UserAddress.builder()
                .user(user)
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .postCode(postCode)
                .address(address)
                .addressDetail(addressDetail)
                .addressExtra(addressExtra)
                .build();
    }
}
