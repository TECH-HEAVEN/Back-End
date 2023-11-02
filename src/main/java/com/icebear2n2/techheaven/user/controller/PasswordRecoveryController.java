package com.icebear2n2.techheaven.user.controller;

import com.icebear2n2.techheaven.domain.request.PasswordRecoveryRequest;
import com.icebear2n2.techheaven.user.service.PasswordRecoveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/password/recovery")
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    @Operation(summary = "비밀번호 복구 코드 요청",
            description = "특정 사용자 ID에 대한 비밀번호 복구 코드를 요청합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "비밀번호 복구 코드 요청이 성공적으로 처리됨",
                            content = @Content(schema = @Schema(implementation = SingleMessageSentResponse.class)))
            })
    @PostMapping
    public ResponseEntity<SingleMessageSentResponse> requestCode(@Parameter(description = "비밀번호 복구 코드를 요청할 사용자 ID") @RequestBody Long userId
    ) {
        SingleMessageSentResponse sentResponse = passwordRecoveryService.requestPasswordRecovery(userId);
        return new ResponseEntity<>(sentResponse, HttpStatus.OK);
    }

    @Operation(summary = "비밀번호 재설정",
            description = "특정 사용자 ID에 대한 비밀번호를 재설정합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "비밀번호가 성공적으로 재설정됨",
                            content = @Content(schema = @Schema(implementation = String.class)))
            })
    @PutMapping("/update")
    public ResponseEntity<String> resetPassword(
            @RequestBody
            @Parameter(description = "비밀번호 복구 및 재설정 요청에 필요한 정보") PasswordRecoveryRequest passwordRecoveryRequest) {
        passwordRecoveryService.verifyAuthCodeAndResetPassword(passwordRecoveryRequest);
        return new ResponseEntity<>("Password reset was successful.", HttpStatus.OK);
    }
}