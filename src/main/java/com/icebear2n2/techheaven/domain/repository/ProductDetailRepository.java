package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    //특정 색상을 포함하는 제품 상세 정보 조회
    List<ProductDetail> findByProductColorsContains(String color);

    //특정 사이즈를 포함하는 제품 상세 정보 조회
    List<ProductDetail> findByProductSizesContains(String size);


    //특정 날짜 이후에 업데이트된 제품 상세 정보 조회
    List<ProductDetail> findByUpdatedAtAfter(Timestamp updatedAt);


    //특정 색상과 사이즈를 모두 포함하는 제품 상세 정보 조회
    List<ProductDetail> findByProductColorsContainsAndProductSizesContains(String color, String size);


}
