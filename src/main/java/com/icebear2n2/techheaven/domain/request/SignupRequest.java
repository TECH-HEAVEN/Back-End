package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.Role;
import com.icebear2n2.techheaven.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원 가입 요청 모델")
public class SignupRequest {

    @Schema(description = "사용자 이름", example = "test")
    private String username;

    @Schema(description = "사용자 이메일", example = "test@example.com")
    private String email;

    @Schema(description = "사용자 비밀번호")
    private String password;

    public User toEntity(String username, String email, String password) {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(Role.ROLE_USER)
                .build();
    }
}