package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressResponse {

    private boolean success;
    private String message;
    private UserAddressData data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserAddressData {
        private Long userAddressId;
        private Long userId;
        private String receiverName;
        private String receiverPhone;

        private String postCode;
        private String address;
        private String addressDetail;
        private String addressExtra;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public UserAddressData(UserAddress userAddress) {
            this.userAddressId = userAddress.getUserAddressId();
            this.userId = userAddress.getUser().getUserId();
            this.receiverName = userAddress.getReceiverName();
            this.receiverPhone = userAddress.getReceiverPhone();
            this.postCode = userAddress.getPostCode();
            this.address = userAddress.getAddress();
            this.addressDetail = userAddress.getAddressDetail() != null ? userAddress.getAddressDetail() : null;
            this.addressExtra = userAddress.getAddressExtra() != null ? userAddress.getAddressExtra() : null;
            this.createdAt = userAddress.getCreatedAt();
            this.updatedAt = userAddress.getUpdatedAt();
        }
    }
    public static UserAddressResponse success(UserAddress userAddress) {
        return new UserAddressResponse(true, "Success", new UserAddressData(userAddress));
    }

    public static UserAddressResponse failure(String message) {
        return new UserAddressResponse(false, message, null);
    }

}
