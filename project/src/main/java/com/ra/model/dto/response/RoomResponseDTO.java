package com.ra.model.dto.response;

import com.ra.model.entity.Room;
import jakarta.validation.constraints.Min;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoomResponseDTO {
    private String roomNo;
    private String image;
    private String description;
    private Float price;
    private String roomType;
    private String hotelName;
    private Boolean status;

    public RoomResponseDTO(Room room) {
        this.roomNo = room.getRoomNo();
        this.image = room.getImage();
        this.description = room.getDescription();
        this.price = room.getPrice();
        this.roomType = room.getRoomType().getType();
        this.hotelName = room.getHotel().getHotelName();
        this.status = room.getStatus();
    }
}
