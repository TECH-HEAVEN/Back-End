package com.icebear2n2.techheaven.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "인증 코드 확인 요청 모델")
public class CheckAuthCodeRequest {
    @Schema(description = "인증 코드를 확인할 사용자 ID")
    private Long userId;

    @Schema(description = "인증 코드를 받았던 전화 번호")
    private String phone;
    @Schema(description = "확인할 인증 코드")
    private String code;
}
