package com.ra.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HotelRequestDTO {
    @NotEmpty(message = "Không được để trống !")
    private String hotelName;
    @NotEmpty(message = "Không được để trống !")
    private String address;
    private Long cityId;
    private String description;
    private MultipartFile image;
    private Boolean status = true;
}
