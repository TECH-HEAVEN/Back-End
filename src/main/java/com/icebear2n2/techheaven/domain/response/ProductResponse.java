package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private boolean success;
    private String message;
    private ProductData data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "상품 데이터 내용")
    public static class ProductData {

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "카테고리 이름", example = "하드웨어")
        private String categoryName;

        @Schema(description = "상품 이름", example = "시놀로지 NAS")
        private String productName;

        @Schema(description = "상품 가격", example = "1000000")
        private Integer productPrice;

        @Schema(description = "할인 가격", example = "900000")
        private Integer discountPrice;

        @Schema(description = "세일 시작일", example = "2023-01-01T10:00:00.000Z")
        private String saleStartDate;

        @Schema(description = "세일 종료일", example = "2023-01-10T10:00:00.000Z")
        private String saleEndDate;

        @Schema(description = "생성일", example = "2023-01-01T10:00:00.000Z")
        private String createdAt;

        @Schema(description = "수정일", example = "2023-01-10T10:00:00.000Z")
        private String updatedAt;

        public ProductData(Product product) {
            this.productId = product.getProductId();
            this.categoryName = product.getCategory().getCategoryName();
            this.productName = product.getProductName();
            this.productPrice = product.getProductPrice();
            this.discountPrice = product.getDiscountPrice();
            this.saleStartDate = product.getSaleStartDate() != null ? product.getSaleStartDate().toString() : null;
            this.saleEndDate = product.getSaleEndDate() != null ? product.getSaleEndDate().toString() : null;
            this.createdAt = product.getCreatedAt().toString();
            this.updatedAt = product.getUpdatedAt().toString();
        }
    }

    public static ProductResponse success(Product product) {
        return new ProductResponse(true, "Success", new ProductData(product));
    }

    public static ProductResponse failure(String message) {
        return new ProductResponse(false, message, null);
    }
}