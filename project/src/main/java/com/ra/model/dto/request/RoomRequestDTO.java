package com.ra.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoomRequestDTO {
    @NotEmpty(message = "Không được để trống !")
    private String roomNo;
    private MultipartFile image;
    private String description;
    @Pattern(regexp = "\\d+", message = "Không đúng định dạng số !")
    @Min(value = 1, message = "Giá không thể nhỏ hơn 1 !")
    private String price;
    private Long typeId;
    private Long hotelId;
    private Boolean status = true;
}
