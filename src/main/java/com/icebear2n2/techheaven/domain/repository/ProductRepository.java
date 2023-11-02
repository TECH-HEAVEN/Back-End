package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByProductId(Long productId);
    boolean existsByProductName(String ProductName);
    Product findByProductName(String productName);

    //특정 가격 이상의 제품 조회
    List<Product> findByProductPriceGreaterThanEqual(Integer price);

    //특정 할인 가격 이하의 제품 조회
    List<Product> findByDiscountPriceLessThanEqual(Integer discountPrice);

    //판매 시작 날짜를 기준으로 오래된 제품 조회
    List<Product> findBySaleStartDateBefore(Timestamp date);

    //판매 종료 날짜를 기준으로 제품 조회
    List<Product> findBySaleEndDateAfter(Timestamp date);

    //특정 카테고리의 제품 조회
    List<Product> findByCategoryContaining(String categoryName);

    //특정 날짜 이후에 추가된 제품 조회
    List<Product> findByCreatedAtAfter(Timestamp createdAt);

    List<Product> findBySaleStartDateBeforeAndSaleEndDateAfter(Timestamp currentTimestamp, Timestamp currentTimestamp1);
}
