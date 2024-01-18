package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String hotelName;
    private String address;
    @ManyToOne
    @JoinColumn(name = "city_id",referencedColumnName = "id")
    private City city;
    private String description;
    private String image;
    @Column(columnDefinition = "boolean default true")
    private Boolean status = true;
    @OneToMany(mappedBy = "hotel",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Room> rooms;
}
