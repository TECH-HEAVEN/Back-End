package com.icebear2n2.techheaven.user.controller;

import com.icebear2n2.techheaven.domain.entity.User;
import com.icebear2n2.techheaven.domain.request.LoginRequest;
import com.icebear2n2.techheaven.domain.request.SignupRequest;
import com.icebear2n2.techheaven.security.oauth2.PrincipalDetails;
import com.icebear2n2.techheaven.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/users")
public class UserController {

    //UserController: 사용자 정보 조회 기능 처리.
    private final UserService userService;

    @Operation(summary = "사용자 등록", description = "새 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }


    @Operation(summary = "사용자 인증", description = "사용자 인증을 위한 토큰을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적인 인증"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")

    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Map<String, String> token = userService.authenticateUser(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @Operation(summary = "현재 사용자 정보 조회")
    @GetMapping

    public ResponseEntity<User> getUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(principalDetails.getUser());
    }

    @Operation(summary = "여러 사용자 정보 조회",
            description = "다양한 필터 조건을 사용하여 사용자 정보를 조회합니다.",
            parameters = {
                    @Parameter(name = "email", description = "이메일로 사용자 조회"),
                    @Parameter(name = "username", description = "사용자 이름으로 사용자 조회"),
                    @Parameter(name = "provider", description = "제공자로 사용자 조회"),
                    @Parameter(name = "phone", description = "전화번호로 사용자 조회"),
                    @Parameter(name = "updateDate", description = "업데이트 날짜를 기준으로 사용자 조회"),
                    @Parameter(name = "page", description = "페이지 번호", example = "0"),
                    @Parameter(name = "size", description = "한 페이지에 표시될 항목 수", example = "10")
            })
    @GetMapping("/all")
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Timestamp updateDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (email != null && !email.trim().isEmpty()) {
            return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
        }
        if (username != null && !username.trim().isEmpty()) {
            return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
        }
        if (provider != null && !provider.trim().isEmpty()) {
            return new ResponseEntity<>(userService.getUsersByProvider(provider), HttpStatus.OK);
        }
        if (phone != null && !phone.trim().isEmpty()) {
            return new ResponseEntity<>(userService.getUserByPhone(phone), HttpStatus.OK);
        }
        if (updateDate != null) {
            return new ResponseEntity<>(userService.getUsersUpdatedAfter(updateDate), HttpStatus.OK);
        }

        return new ResponseEntity<>(userService.getRecentUsers(pageable), HttpStatus.OK);
    }

    @Operation(summary = "특정 사용자 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<String> removeUser(@RequestBody Long userId) {
        userService.removeUser(userId);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }

}
