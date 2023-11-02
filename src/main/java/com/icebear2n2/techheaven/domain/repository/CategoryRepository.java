package com.icebear2n2.techheaven.domain.repository;

import com.icebear2n2.techheaven.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);
    Category findByCategoryName(String categoryName);


    //특정 날짜 이후에 생성된 카테고리 조회
    List<Category> findByCreatedAtAfter(Timestamp createdAt);

    //특정 날짜 이후에 업데이트된 카테고리 조회
    List<Category> findByUpdatedAtAfter(Timestamp updatedAt);

    //카테고리 이름에 특정 문자열이 포함된 카테고리 조회
    List<Category> findByCategoryNameContaining(String keyword);

    //카테고리 이름을 알파벳/한글 순으로 정렬하여 조회
    List<Category> findByOrderByCategoryNameAsc();

    //최근에 업데이트된 카테고리 순으로 조회
    List<Category> findByOrderByUpdatedAtDesc();

}
