package com.ra.model.dto.response;

import com.ra.model.entity.RoomType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoomTypeResponseDTO {
    private String type;
    private String description;
    private Boolean status;

    public RoomTypeResponseDTO(RoomType roomType) {
        this.type = roomType.getType();
        this.description = roomType.getDescription();
        this.status = roomType.getStatus();
    }
}
