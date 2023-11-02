package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.Category;
import com.icebear2n2.techheaven.domain.entity.Product;
import com.icebear2n2.techheaven.domain.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "상품 생성 및 업데이트 요청 모델")
public class ProductRequest {

    @Schema(description = "상품 ID")
    private Long productId;
    @Schema(description = "카테고리 이름", example = "프로그래밍 도서")
    private String categoryName;

    @Schema(description = "상품 이름", example = "스프링부트 교과서")
    private String productName;

    @Schema(description = "상품 가격", example = "20000")
    private Integer productPrice;


    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .productPrice(productPrice)
                .build();
    }

    public void updateProductIfNotNull(Product product, CategoryRepository categoryRepository) {
        if (this.categoryName != null) {
            Category category = categoryRepository.findByCategoryName(this.categoryName);
            if (category != null) {
                product.setCategory(category);
            }
        }
        if (this.productName != null) {
            product.setProductName(this.productName);
        }
        if (this.productPrice != null) {
            product.setProductPrice(this.productPrice);
        }
    }
}

