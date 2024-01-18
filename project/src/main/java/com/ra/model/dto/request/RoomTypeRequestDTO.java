package com.ra.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoomTypeRequestDTO {
    @NotEmpty(message = "Không được để trống !")
    private String type;
    private String description;
    private Boolean status = true;
}
