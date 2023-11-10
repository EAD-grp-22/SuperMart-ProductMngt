package com.supermart.inventorymanagement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    @NotNull(message = "Product ID is required")
    @NotBlank(message = "Product ID is required")
    private String id;

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    @DecimalMin(value = "0.0", message = "Price must be a non-negative number")
    private Double price;

    @Min(value = 0, message = "Quantity must be a non-negative number")
    private int quantity;

    @Size(min = 1, max = 255, message = "Brand must be between 1 and 255 characters")
    private String brand;

    @Size(min = 1, max = 255, message = "Category must be between 1 and 255 characters")
    private String category;
}
