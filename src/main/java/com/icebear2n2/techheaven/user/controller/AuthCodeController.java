package com.icebear2n2.techheaven.user.controller;

import com.icebear2n2.techheaven.domain.request.CheckAuthCodeRequest;
import com.icebear2n2.techheaven.domain.request.PhoneRequest;
import com.icebear2n2.techheaven.user.service.AuthCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verification")
public class AuthCodeController {

    private final AuthCodeService authCodeService;

    @Operation(summary = "인증 코드 전송",
            description = "전화 번호로 인증 코드를 전송합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증 코드 전송 성공",
                            content = @Content(schema = @Schema(implementation = SingleMessageSentResponse.class)))
            })
    @PostMapping
    public ResponseEntity<SingleMessageSentResponse> sendAuthCode(@RequestBody PhoneRequest phone) {
        SingleMessageSentResponse sentResponse = authCodeService.sendAuthCode(phone);
        return new ResponseEntity<>(sentResponse, HttpStatus.OK);
    }

    @Operation(summary = "인증 코드 확인",
            description = "사용자 ID와 전화 번호, 그리고 코드를 사용하여 인증 코드를 확인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증 코드 확인 성공",
                            content = @Content(schema = @Schema(implementation = String.class)))
            })
    @GetMapping
    public ResponseEntity<String> checkAuthCode(@RequestBody CheckAuthCodeRequest checkAuthCodeRequest) {
        authCodeService.checkAuthCode(checkAuthCodeRequest);
        return new ResponseEntity<>("CHECK AUTH CODE SUCCESSFULLY.", HttpStatus.OK);
    }

    @Operation(summary = "잔액 조회",
            description = "서비스의 현재 잔액을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "잔액 조회 성공",
                            content = @Content(schema = @Schema(implementation = Balance.class)))
            })
    @GetMapping("/balance")
    public ResponseEntity<Balance> getBalance() {
        Balance balance = authCodeService.getBalance();
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
