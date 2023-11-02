package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.UserAddress;
import com.icebear2n2.techheaven.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    boolean existsByUserAddressId(Long userAddressId);
    boolean findByUser(User user);
}
