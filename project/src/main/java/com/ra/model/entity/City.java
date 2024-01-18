package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cityName;
    @OneToMany(mappedBy = "city",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Hotel> hotels;
//    @Column(columnDefinition = "boolean default true")
    private Boolean status;
}
