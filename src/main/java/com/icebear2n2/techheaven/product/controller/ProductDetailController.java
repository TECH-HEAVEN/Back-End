package com.icebear2n2.techheaven.product.controller;

import com.icebear2n2.techheaven.domain.request.ProductDetailRequest;
import com.icebear2n2.techheaven.domain.response.ProductDetailResponse;
import com.icebear2n2.techheaven.product.service.ProductDetailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/details")
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    /**
     * 새로운 상품 상세 정보를 생성합니다.
     *
     * @param productDetailRequest 상품 상세 정보 요청 본문.
     * @return 상태와 데이터를 포함한 응답.
     */
    @Operation(summary = "상품 상세 정보 생성")
    @PostMapping
    public ResponseEntity<ProductDetailResponse> createProductDetail(@RequestBody ProductDetailRequest productDetailRequest) {
        ProductDetailResponse productDetailResponse = productDetailService.createProductDetail(productDetailRequest);
        if (productDetailResponse.isSuccess()) {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 색상, 사이즈, 업데이트 시간 등의 조건을 기반으로 상품 상세 정보를 검색합니다.
     *
     * @return 조건에 맞는 상품 상세 정보 목록.
     */
    @Operation(summary = "상품 상세 정보 검색")
    @GetMapping
    public ResponseEntity<?> getProductDetails(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Timestamp updatedAt) {

        if (color != null && size == null) {
            return new ResponseEntity<>(productDetailService.findProductDetailsByColor(color), HttpStatus.OK);
        } else if (size != null && color == null) {
            return new ResponseEntity<>(productDetailService.findProductDetailsBySize(size), HttpStatus.OK);
        } else if (updatedAt != null) {
            return new ResponseEntity<>(productDetailService.findProductDetailsUpdatedAfter(updatedAt), HttpStatus.OK);
        } else if (color != null) {
            return new ResponseEntity<>(productDetailService.findProductDetailsByColorAndSize(color, size), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productDetailService.getAllProductDetails(), HttpStatus.OK);
        }
    }

    /**
     * 기존 상품 상세 정보를 업데이트합니다.
     *
     * @param productDetailRequest 상품 상세 정보 요청 본문.
     * @return 상태와 데이터를 포함한 응답.
     */
    @Operation(summary = "상품 상세 정보 업데이트")
    @PutMapping("/update")
    public ResponseEntity<ProductDetailResponse> updateProductDetail(@RequestBody ProductDetailRequest productDetailRequest) {
        ProductDetailResponse productDetailResponse = productDetailService.updateProductDetail( productDetailRequest);

        if (productDetailResponse.isSuccess()) {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 상품 상세 정보를 삭제합니다.
     *
     * @param productDetailId 삭제할 상품 상세 정보의 ID.
     * @return 삭제 성공 메시지와 함께하는 응답.
     */
    @Operation(summary = "상품 상세 정보 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<String> removeProductDetail(@RequestBody Long productDetailId) {
        productDetailService.removeProductDetail(productDetailId);
        return new ResponseEntity<>("Product Detail removed successfully.", HttpStatus.OK);
    }
}