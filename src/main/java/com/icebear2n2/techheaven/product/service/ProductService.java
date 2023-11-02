package com.icebear2n2.techheaven.product.service;

import com.icebear2n2.techheaven.domain.entity.Product;
import com.icebear2n2.techheaven.domain.entity.ProductDetail;
import com.icebear2n2.techheaven.domain.repository.CategoryRepository;
import com.icebear2n2.techheaven.domain.repository.ProductDetailRepository;
import com.icebear2n2.techheaven.domain.repository.ProductRepository;
import com.icebear2n2.techheaven.domain.request.ProductRequest;
import com.icebear2n2.techheaven.domain.response.ProductResponse;
import com.icebear2n2.techheaven.exception.ErrorCode;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    public ProductResponse createProduct(ProductRequest productRequest) {
        if (!categoryRepository.existsByCategoryName(productRequest.getCategoryName())) {
            return ProductResponse.failure(ErrorCode.CATEGORY_NOT_FOUND.toString());
        }

        if (productRepository.existsByProductName(productRequest.getProductName())) {
            return ProductResponse.failure(ErrorCode.DUPLICATED_PRODUCT_NAME.toString());
        }

        try {
            Product product = productRequest.toEntity();
            product.setCategory(categoryRepository.findByCategoryName(productRequest.getCategoryName()));
            Product savedProduct = productRepository.save(product);
            return ProductResponse.success(savedProduct);
        } catch (Exception e) {
            LOGGER.error("Error occurred while creating product", e);
            return ProductResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }


    public ProductResponse findProductByName(String productName) {
        Product product = productRepository.findByProductName(productName);
        if (product != null) {
            return ProductResponse.success(product);
        }
        return ProductResponse.failure(ErrorCode.PRODUCT_NOT_FOUND.toString());
    }

    public List<ProductResponse.ProductData> findProductsByPrice(Integer price) {
        return productRepository.findByProductPriceGreaterThanEqual(price)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsByDiscountPrice(Integer discountPrice) {
        return productRepository.findByDiscountPriceLessThanEqual(discountPrice)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsBySaleStartDate(Timestamp date) {
        return productRepository.findBySaleStartDateBefore(date)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsBySaleEndDate(Timestamp date) {
        return productRepository.findBySaleEndDateAfter(date)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsByCategory(String categoryName) {
        return productRepository.findByCategoryContaining(categoryName)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsAddedAfter(Timestamp createdAt) {
        return productRepository.findByCreatedAtAfter(createdAt)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsBySaleStartAndEndDate(Timestamp startTimestamp, Timestamp endTimestamp) {
        return productRepository.findBySaleStartDateBeforeAndSaleEndDateAfter(startTimestamp, endTimestamp)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    //   TODO: UPDATE
    public ProductResponse updateProduct(ProductRequest productRequest) {

        if (!productRepository.existsByProductId(productRequest.getProductId())) {
            return ProductResponse.failure(ErrorCode.PRODUCT_NOT_FOUND.toString());
        }

        if (!categoryRepository.existsByCategoryName(productRequest.getCategoryName())) {
            return ProductResponse.failure(ErrorCode.CATEGORY_NOT_FOUND.toString());
        }

        if (productRepository.existsByProductName(productRequest.getProductName())) {
            return ProductResponse.failure(ErrorCode.DUPLICATED_PRODUCT_NAME.toString());
        }

        try {
            Product existingProduct = productRepository.findById(productRequest.getProductId())
                    .orElseThrow(() -> new TechHeavenException(ErrorCode.PRODUCT_NOT_FOUND));
            productRequest.updateProductIfNotNull(existingProduct, categoryRepository);
            productRepository.save(existingProduct);
            return ProductResponse.success(existingProduct);
        } catch (Exception e) {
            LOGGER.info("ERROR OCCURS {}", e.toString());
            return ProductResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public void removeProduct(Long productId) {
        if (!productRepository.existsByProductId(productId)) {
            throw new TechHeavenException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        try {
            productRepository.deleteById(productId);
        } catch (DataIntegrityViolationException e) {
            throw new TechHeavenException(ErrorCode.PRODUCT_HAS_RELATED_PRODUCT_DETAIL);
        } catch (Exception e) {
            throw new TechHeavenException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void removeProductAndDetails(Long productId) {
        // Step1: 상품에 연결된 상세 정보 찾기
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new TechHeavenException(ErrorCode.PRODUCT_NOT_FOUND));

        // Step2: 상품 관련 상세 정보 전체 삭제
        List<ProductDetail> productDetails = product.getProductDetails();
        productDetailRepository.deleteAll(productDetails);

        // Step3: 상품 삭제
        productRepository.delete(product);
    }
}