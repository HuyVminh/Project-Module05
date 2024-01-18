package com.ra.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.model.entity.Hotel;
import com.ra.model.entity.Room;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HotelResponseDTO {
    private String hotelName;
    private String address;
    private String city;
    private String description;
    private String image;
    private Boolean status;

    public HotelResponseDTO(Hotel hotel) {
        this.hotelName = hotel.getHotelName();
        this.address = hotel.getAddress();
        this.description = hotel.getDescription();
        this.image = hotel.getImage();
        this.city = hotel.getCity().getCityName();
        this.status = hotel.getStatus();
    }
}
