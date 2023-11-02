package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.entity.Role;
import com.icebear2n2.techheaven.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "사용자 응답 모델")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @Schema(description = "요청 성공 여부")
    private boolean success;

    @Schema(description = "메시지")
    private String message;

    @Schema(description = "사용자 데이터")
    private UserData data;

    @Schema(description = "사용자 데이터 내부 클래스")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserData {

        @Schema(description = "사용자 ID")
        private Long userId;

        @Schema(description = "사용자 이름")
        private String username;

        @Schema(description = "사용자 이메일")
        private String email;

        @Schema(description = "인증 제공자")
        private String provider;

        @Schema(description = "제공자의 사용자 ID")
        private String providerUserId;

        @Schema(description = "사용자 전화번호")
        private String userPhone;

        @Schema(description = "사용자 역할")
        private Enum<Role> role;

        @Schema(description = "계정 생성 일시")
        private String createdAt;

        @Schema(description = "계정 수정 일시")
        private String updatedAt;

        @Schema(description = "계정 삭제 일시")
        private String deletedAt;

        public UserData(User user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.provider = user.getProvider();
            this.providerUserId = user.getProviderUserId();
            this.userPhone = user.getUserPhone();
            this.role = user.getRole();
            this.createdAt = user.getCreatedAt().toString();
            this.updatedAt = user.getUpdatedAt().toString();
            this.deletedAt = user.getDeletedAt() != null ? user.getDeletedAt().toString() : null;
        }
    }

    public static UserResponse success(User user) {
        return new UserResponse(true, "Success", new UserData(user));
    }

    public static UserResponse failure(String message) {
        return new UserResponse(false, message, null);
    }
}