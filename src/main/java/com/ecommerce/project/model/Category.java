package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Lombok automatically will add the getter/setter , constructors.
@Entity(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    //GenerationType.IDENTITY - for Primary key, not supported by all db's
    //GenerationType.Auto - for JPA to decide on generation of keys.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message = "Category Name cannot be blank !!")
    @Size(min=5, message = "Category name must be of atleast 5 characters")
    private String categoryName;
//
//    public Long getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
//
//    public Category() {
//    }
//
//    public Category(String categoryName, Long categoryId) {
//        this.categoryName = categoryName;
//        this.categoryId = categoryId;
//    }
}
