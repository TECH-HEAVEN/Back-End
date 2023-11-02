package com.icebear2n2.techheaven.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "비밀번호 복구 요청 모델")
public class PasswordRecoveryRequest {

    @Schema(description = "비밀번호를 재설정할 사용자 ID")
    private Long userId;
    @Schema(description = "인증 코드")
    private String code;

    @Schema(description = "새로운 비밀번호")
    private String newPassword;

    @Schema(description = "새로운 비밀번호 확인")
    private String confirmNewPassword;
}