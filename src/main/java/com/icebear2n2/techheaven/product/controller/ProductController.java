package com.icebear2n2.techheaven.product.controller;

import com.icebear2n2.techheaven.domain.request.ProductRequest;
import com.icebear2n2.techheaven.domain.response.ProductResponse;
import com.icebear2n2.techheaven.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    /**
     * 새로운 상품을 생성합니다.
     *
     * @param productRequest 상품 상세 정보를 포함한 요청 본문.
     * @return 상태와 데이터를 포함한 응답.
     */
    @Operation(summary = "새로운 상품 생성")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        if (productResponse.isSuccess()) {
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(productResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 상품 이름, 카테고리, 가격 등의 조건을 기반으로 상품을 검색합니다.
     *
     * @return 조건에 맞는 상품 목록.
     */
    @Operation(summary = "상품 검색")
    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) Integer discountPrice,
            @RequestParam(required = false) Timestamp saleStartDate,
            @RequestParam(required = false) Timestamp saleEndDate,
            @RequestParam(required = false) Timestamp createdAt,
            @RequestParam(required = false) Timestamp saleStartTimestamp,
            @RequestParam(required = false) Timestamp saleEndTimestamp) {

        if (productName != null) {
            return new ResponseEntity<>(productService.findProductByName(productName), HttpStatus.OK);
        } else if (categoryName != null) {
            return new ResponseEntity<>(productService.findProductsByCategory(categoryName), HttpStatus.OK);
        } else if (price != null) {
            return new ResponseEntity<>(productService.findProductsByPrice(price), HttpStatus.OK);
        } else if (discountPrice != null) {
            return new ResponseEntity<>(productService.findProductsByDiscountPrice(discountPrice), HttpStatus.OK);
        } else if (saleStartDate != null) {
            return new ResponseEntity<>(productService.findProductsBySaleStartDate(saleStartDate), HttpStatus.OK);
        } else if (saleEndDate != null) {
            return new ResponseEntity<>(productService.findProductsBySaleEndDate(saleEndDate), HttpStatus.OK);
        } else if (createdAt != null) {
            return new ResponseEntity<>(productService.findProductsAddedAfter(createdAt), HttpStatus.OK);
        } else if (saleStartTimestamp != null && saleEndTimestamp != null) {
            return new ResponseEntity<>(productService.findProductsBySaleStartAndEndDate(saleStartTimestamp, saleEndTimestamp), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        }
    }

    /**
     * 기존 상품 정보를 업데이트합니다.
     *
     * @param productRequest 상품 상세 정보를 포함한 요청 본문.
     * @return 상태와 데이터를 포함한 응답.
     */
    @Operation(summary = "상품 정보 업데이트")
    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct( @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(productRequest);
        if (productResponse.isSuccess()) {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 상품을 삭제합니다.
     *
     * @param productId 삭제할 상품의 ID.
     * @return 삭제 성공 메시지와 함께하는 응답.
     */
    @Operation(summary = "상품 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<String> removeProduct(@RequestBody Long productId) {
        productService.removeProduct(productId);
        return new ResponseEntity<>("Product removed successfully.", HttpStatus.OK);
    }

    /**
     * 상품과 그에 관련된 상품 세부 정보를 삭제합니다.
     *
     * @param productId 삭제할 상품의 ID.
     * @return 삭제 성공 메시지와 함께하는 응답.
     */
    @Operation(summary = "상품 및 상품 세부 정보 삭제")
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> removeProductAndDetails(@RequestBody Long productId) {
        productService.removeProductAndDetails(productId);
        return new ResponseEntity<>("Product and product Details removed successfully.", HttpStatus.OK);
    }
}