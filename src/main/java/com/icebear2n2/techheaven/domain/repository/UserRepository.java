package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 주어진 이메일로 사용자가 존재하는지 확인
    Boolean existsByEmail(String email);

    // 주어진 이메일로 사용자 조회
    User findByEmail(String email);

    // 주어진 사용자명으로 사용자 조회
    User findByUsername(String username);

    // 주어진 Provider 와 Provider 사용자 아이디로 사용자 조회
    Optional<User> findByProviderAndProviderUserId(String provider, String providerId);

    // 주어진 시간보다 이전에 삭제된 사용자 삭제
    void deleteByDeletedAtBefore(Timestamp threshold);

    // Provider 별 사용자 조회
    List<User> findByProvider(String provider);

    // 최근에 가입한 사용자 조회 (예: 최근 10명)
    List<User> findTop10ByOrderByCreatedAtDesc(Pageable pageable);

    // 특정 전화번호를 가진 사용자 조회
    User findByUserPhone(String userPhone);

    // 사용자의 특정 업데이트 이후 사용자 조회
    List<User> findByUpdatedAtAfter(Timestamp updateDate);

}