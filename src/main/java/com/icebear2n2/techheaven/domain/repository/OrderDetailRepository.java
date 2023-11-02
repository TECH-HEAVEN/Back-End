package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
