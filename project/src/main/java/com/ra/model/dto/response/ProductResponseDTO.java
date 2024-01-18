package com.ra.model.dto.response;

import com.ra.model.entity.Product;
import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponseDTO {
    private String productName;
    private Float price;
    private String description;
    private Boolean status;

    public ProductResponseDTO(Product product) {
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.status = product.getStatus();
    }
}
