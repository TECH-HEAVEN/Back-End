package com.icebear2n2.techheaven.domain.repository;


import com.icebear2n2.techheaven.domain.entity.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
    AuthCode findByPhoneAndCode(String phone, String code);

    AuthCode findByCode(String code);
}
