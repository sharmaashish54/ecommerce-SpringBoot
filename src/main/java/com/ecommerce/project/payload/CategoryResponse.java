package com.ecommerce.project.payload;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> content;
    private Integer pageSize;
    private Integer pageNumber;
    private  Integer totalPages;
    private Long totalElements;
    private boolean lastPage;

}