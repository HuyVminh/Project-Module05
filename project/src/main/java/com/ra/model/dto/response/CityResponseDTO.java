package com.ra.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.model.entity.City;
import com.ra.model.entity.Hotel;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CityResponseDTO {
    private Long id;
    private String cityName;
    private Boolean status;

    public CityResponseDTO(City city) {
        this.id = city.getId();
        this.cityName = city.getCityName();
        this.status = city.getStatus();
    }
}
