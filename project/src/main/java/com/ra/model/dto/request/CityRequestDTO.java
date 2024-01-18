package com.ra.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CityRequestDTO {
    @NotEmpty(message = "Không được để trống !")
    private String cityName;
    private Boolean status = true;
}
