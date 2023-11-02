package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "카테고리 생성 및 업데이트 요청 모델")
public class CategoryRequest {
    @Schema(description = "카테고리 ID")
    private Long categoryId;
    @Schema(description = "카테고리 이름", example = "프로그래밍 도서")
    private String categoryName;

    /**
     * 카테고리 요청 객체를 엔터티로 변환합니다.
     *
     * @return Category 엔터티
     */
    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }

    /**
     * 값이 존재하는 경우 카테고리를 업데이트합니다.
     *
     * @param category 업데이트할 카테고리 엔터티
     */
    public void updateCategoryIfNotNull(Category category) {
        if (this.categoryName != null) {
            category.setCategoryName(this.categoryName);
        }
    }
}