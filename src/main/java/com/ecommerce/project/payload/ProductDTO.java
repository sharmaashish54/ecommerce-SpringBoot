package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;
    private double price;
    private String productName;
    private String image;
    private String description;
    private Integer quantity;
    private double specialPrice;
    private double discount;

}
