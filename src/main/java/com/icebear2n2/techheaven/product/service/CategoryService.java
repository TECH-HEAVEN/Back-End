package com.icebear2n2.techheaven.product.service;

import com.icebear2n2.techheaven.domain.entity.Category;
import com.icebear2n2.techheaven.domain.repository.CategoryRepository;
import com.icebear2n2.techheaven.domain.request.CategoryRequest;
import com.icebear2n2.techheaven.domain.response.CategoryResponse;
import com.icebear2n2.techheaven.exception.ErrorCode;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            return CategoryResponse.failure(ErrorCode.DUPLICATED_CATEGORY_NAME.toString());
        }

        try {
            return CategoryResponse.success(categoryRepository.save(categoryRequest.toEntity()));
        } catch (Exception e) {
            return CategoryResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public List<CategoryResponse.CategoryData> findCategoriesCreatedAfter(Timestamp createdAt) {
        return categoryRepository.findByCreatedAtAfter(createdAt)
                .stream()
                .map(CategoryResponse.CategoryData::new)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse.CategoryData> findCategoriesUpdatedAfter(Timestamp updatedAt) {
        return categoryRepository.findByUpdatedAtAfter(updatedAt)
                .stream()
                .map(CategoryResponse.CategoryData::new)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse.CategoryData> findCategoriesByNameContaining(String keyword) {
        return categoryRepository.findByCategoryNameContaining(keyword)
                .stream()
                .map(CategoryResponse.CategoryData::new)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse.CategoryData> findAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponse.CategoryData::new).collect(Collectors.toList());
    }

//    TODO: UPDATE

    public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
        if (!categoryRepository.existsById(categoryRequest.getCategoryId())) {
            return CategoryResponse.failure(ErrorCode.CATEGORY_NOT_FOUND.toString());
        }

        List<Category> categoriesWithSameName = categoryRepository.findByCategoryNameContaining(categoryRequest.getCategoryName());

        boolean isNameTakenByAnotherCategory = categoriesWithSameName.stream()
                .anyMatch(category -> !category.getCategoryId().equals(categoryRequest.getCategoryId()));

        if (isNameTakenByAnotherCategory) {
            return CategoryResponse.failure(ErrorCode.DUPLICATED_CATEGORY_NAME.toString());
        }

        try {
            Category existingCategory = categoryRepository.findById(categoryRequest.getCategoryId())
                    .orElseThrow(() -> new TechHeavenException(ErrorCode.CATEGORY_NOT_FOUND));
            categoryRequest.updateCategoryIfNotNull(existingCategory);
            categoryRepository.save(existingCategory);
            return CategoryResponse.success(existingCategory);
        } catch (Exception e) {
            return CategoryResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public void removeCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new TechHeavenException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        try {
            categoryRepository.deleteById(categoryId);
        } catch (DataIntegrityViolationException e) {
            throw new TechHeavenException(ErrorCode.CATEGORY_HAS_RELATED_PRODUCTS);
        } catch (Exception e) {
            throw new TechHeavenException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
