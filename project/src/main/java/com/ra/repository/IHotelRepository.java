package com.ra.repository;

import com.ra.model.entity.City;
import com.ra.model.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findAllByHotelNameContainingIgnoreCase(Pageable pageable,String hotelName);
    Boolean existsHotelByHotelName(String hotelName);
    Page<Hotel> findHotelsByCityAndStatus(Pageable pageable,City city,Boolean status);
}
