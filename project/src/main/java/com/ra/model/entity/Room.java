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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String roomNo;
    private String image;
    private String description;
    private Float price;
    @ManyToOne
    @JoinColumn(name = "room_type_id",referencedColumnName = "id")
    private RoomType roomType;
    @ManyToOne
    @JoinColumn(name = "hotel_id",referencedColumnName = "id")
    private Hotel hotel;
    @OneToMany(mappedBy = "room",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Booking> bookings;
    @Column(columnDefinition = "boolean default true")
    private Boolean status = true;
}
