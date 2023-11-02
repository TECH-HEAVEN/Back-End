package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.dto.ProductDTO;
import com.icebear2n2.techheaven.domain.entity.ProductDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "상품 상세 정보 응답 모델")
public class ProductDetailResponse {

    @Schema(description = "응답 성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 메시지", example = "Success")
    private String message;

    @Schema(description = "상품 상세 데이터")
    private ProductDetailData productDetailData;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "상품 상세 데이터 내용")
    public static class ProductDetailData {

        @Schema(description = "상품 정보")
        private ProductDTO product;

        @Schema(description = "상품 색상 목록", example = "[\"Red\", \"Blue\"]")
        private List<String> productColors;

        @Schema(description = "상품 크기 목록", example = "[\"S\", \"M\", \"L\"]")
        private List<String> productSizes;

        @Schema(description = "재고 수량", example = "[10, 20, 30]")
        private List<Integer> stockQuantity;

        @Schema(description = "생성일", example = "2023-01-01T10:00:00.000Z")
        private String createdAt;

        @Schema(description = "수정일", example = "2023-01-10T10:00:00.000Z")
        private String updatedAt;

        public ProductDetailData(ProductDetail productDetail) {
            this.product = new ProductDTO(productDetail.getProduct());
            this.productColors = productDetail.getProductColors() != null ? productDetail.getProductColors() : null;
            this.productSizes = productDetail.getProductSizes() != null ? productDetail.getProductSizes() : null;
            this.stockQuantity = productDetail.getStockQuantity();
            this.createdAt = productDetail.getCreatedAt().toString();
            this.updatedAt = productDetail.getUpdatedAt().toString();
        }
    }

    public static ProductDetailResponse success(ProductDetail productDetail) {
        return new ProductDetailResponse(true, "Success", new ProductDetailData(productDetail));
    }
    public static ProductDetailResponse failure(String message) {
        return new ProductDetailResponse(false, message, null);
    }
}
