package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserAddressRequest {
    private Long userId;
    private Long userAddressId;
    private String receiverName;
    private String receiverPhone;
    private String postCode;
    private String address;
    private String addressDetail;
    private String addressExtra;

    public void updateUserAddressInfoIfNotNull(UserAddress userAddress) {
        if (this.receiverName != null) {
            userAddress.setReceiverName(this.receiverName);
        }

        if (this.receiverPhone != null) {
            userAddress.setReceiverPhone(this.receiverPhone);
        }

        if (this.postCode != null) {
            userAddress.setPostCode(this.postCode);
        }

        if (this.address != null) {
            userAddress.setAddress(this.address);
        }

        if (this.addressDetail != null) {
            userAddress.setAddressDetail(this.addressDetail);
        }

        if (this.addressExtra != null) {
            userAddress.setAddressExtra(this.addressExtra);
        }
    }
}
