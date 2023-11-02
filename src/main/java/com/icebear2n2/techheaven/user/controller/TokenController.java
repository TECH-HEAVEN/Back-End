package com.icebear2n2.techheaven.user.controller;


import com.icebear2n2.techheaven.security.oauth2.PrincipalDetails;
import com.icebear2n2.techheaven.user.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
@Tag(name = "TokenController", description = "JWT 토큰 관련 기능 처리")
public class TokenController {
    private final TokenService tokenService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Operation(summary = "인증된 사용자에 대한 JWT 토큰 생성",
            description = "인증된 사용자에 대한 JWT 토큰을 생성하고 반환합니다.")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Map<String, String>> getTokenForRegularUser(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Map<String, String> tokens = tokenService.generateAndSaveTokens(principalDetails.getUser());
        LOGGER.info("GENERATED TOKEN FOR USER: {}", principalDetails.getUsername());
        return ResponseEntity.ok(tokens);
    }

    @Operation(summary = " OAuth 사용자에 대한 JWT 토큰 생성",
            description = "OAuth 인증된 사용자에 대한 JWT 토큰을 생성하고 반환합니다.")
    @PreAuthorize("hasRole('ROLE_KAKAO_USER')")
    @GetMapping("/oauth")
    public ResponseEntity<Map<String, String>> getTokenForOAuthUser(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Map<String, String> tokens = tokenService.generateAndSaveTokens(principalDetails.getUser());
        LOGGER.info("GENERATED TOKEN FOR  OAUTH USER: {}", principalDetails.getUsername());
        return ResponseEntity.ok(tokens);
    }

    @Operation(summary = "JWT 토큰 정보 조회",
            description = "주어진 JWT 토큰의 클레임 정보를 조회합니다.",
            parameters = {
                    @Parameter(name = "X-Auth-Token", description = "검증하려는 JWT 토큰", required = true, in = ParameterIn.HEADER)
            })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getToken(@RequestHeader("X-Auth-Token") String token) {
        Map<String, Object> claims = tokenService.getClaims(token.replace("Bearer ", ""));
        if (claims == null || claims.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LOGGER.info("claims: " + claims);
        return ResponseEntity.ok(claims);
    }
}