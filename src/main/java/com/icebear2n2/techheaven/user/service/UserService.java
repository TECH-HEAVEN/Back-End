package com.icebear2n2.techheaven.user.service;

import com.icebear2n2.techheaven.domain.entity.User;
import com.icebear2n2.techheaven.domain.repository.UserRepository;
import com.icebear2n2.techheaven.domain.request.LoginRequest;
import com.icebear2n2.techheaven.domain.request.SignupRequest;
import com.icebear2n2.techheaven.domain.response.UserResponse;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import com.icebear2n2.techheaven.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z]).{8,}$");
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new TechHeavenException(ErrorCode.DUPLICATED_USER_EMAIL);
        }

        validatePassword(signupRequest.getPassword());
        String encodedPassword = new BCryptPasswordEncoder().encode(signupRequest.getPassword());
        User user = signupRequest.toEntity(signupRequest.getUsername(), signupRequest.getEmail(), encodedPassword);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("ERROR OCCURRED WHILE SIGNING UP", e);
            throw new TechHeavenException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void validatePassword(String password) {
        if (!password.matches(String.valueOf(PASSWORD_PATTERN))) {
            throw new TechHeavenException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }

    public Map<String, String> authenticateUser(LoginRequest loginRequest) {
        User user = Optional.ofNullable(userRepository.findByEmail(loginRequest.getEmail()))
                .orElseThrow(() -> new TechHeavenException(ErrorCode.USER_NOT_FOUND));

        if (!new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            throw new TechHeavenException(ErrorCode.FAILED_LOGIN);
        }

        return tokenService.generateAndSaveTokens(user);
    }

    public void removeUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TechHeavenException(ErrorCode.USER_NOT_FOUND));

        user.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        user.setRole(null);
        userRepository.save(user);
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void purgeDeletedUsers() {
        Timestamp threshold = new Timestamp(System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000));
        userRepository.deleteByDeletedAtBefore(threshold);
    }


    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return (user != null)
                ? UserResponse.success(user)
                : UserResponse.failure(ErrorCode.USER_EMAIL_NOT_FOUND.toString());
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return (user != null)
                ? UserResponse.success(user)
                : UserResponse.failure(ErrorCode.USERNAME_NOT_FOUND.toString());
    }

    public List<UserResponse> getUsersByProvider(String provider) {
        return userRepository.findByProvider(provider)
                .stream()
                .map(UserResponse::success)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getRecentUsers(Pageable pageable) {
        return userRepository.findTop10ByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(UserResponse::success)
                .collect(Collectors.toList());
    }

    public UserResponse getUserByPhone(String userPhone) {
        User user = userRepository.findByUserPhone(userPhone);
        return (user != null)
                ? UserResponse.success(user)
                : UserResponse.failure(ErrorCode.USER_PHONE_NOT_FOUND.toString());
    }


    public List<UserResponse> getUsersUpdatedAfter(Timestamp updateDate) {
        return userRepository.findByUpdatedAtAfter(updateDate)
                .stream()
                .map(UserResponse::success)
                .collect(Collectors.toList());
    }
}

