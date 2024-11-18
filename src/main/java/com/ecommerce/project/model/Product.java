package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank
    @Size(min = 3,message = "Product name should have atleast 3 characters!!")
    private String productName;
    @NotBlank
    @Size(min = 3,message = "description should have atleast 6 characters!!")
    private String description;
    private double price;
    private Integer quantity;
    private double discount;
    private double specialPrice;
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
