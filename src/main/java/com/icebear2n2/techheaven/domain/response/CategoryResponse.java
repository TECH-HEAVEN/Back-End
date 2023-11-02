package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "카테고리 응답 모델")
public class CategoryResponse {

    @Schema(description = "응답 성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 메시지", example = "Success")
    private String message;

    @Schema(description = "카테고리 데이터")
    private CategoryData data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "카테고리 상세 데이터")
    public static class CategoryData {

        @Schema(description = "카테고리 ID", example = "1")
        private Long categoryId;

        @Schema(description = "카테고리 이름", example = "프로그래밍 도서")
        private String categoryName;

        @Schema(description = "생성일", example = "2023-01-01T10:00:00.000Z")
        private String createdAt;

        @Schema(description = "수정일", example = "2023-01-10T10:00:00.000Z")
        private String updatedAt;

        public CategoryData(Category category) {
            this.categoryId = category.getCategoryId();
            this.categoryName = category.getCategoryName();
            this.createdAt = category.getCreatedAt().toString();
            this.updatedAt = category.getUpdatedAt().toString();
        }
    }

    public static CategoryResponse success(Category category) {
        return new CategoryResponse(true, "Success", new CategoryData(category));
    }

    public static CategoryResponse failure(String message) {
        return new CategoryResponse(false, message, null);
    }
}