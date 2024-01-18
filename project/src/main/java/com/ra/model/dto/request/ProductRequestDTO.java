package com.ra.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequestDTO {
    @NotEmpty(message = "Không được để trống !")
    private String productName;
    @Pattern(regexp = "\\d+", message = "Không đúng định dạng số !")
    @Min(value = 1, message = "Giá không thể nhỏ hơn 1 !")
    private String price;
    private String description;
    private Boolean status = true;
}
