package com.ra.repository;

import com.ra.model.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICityRepository extends JpaRepository<City, Long> {
    Boolean existsByCityName(String cityName);
    Page<City> findAllByCityNameContainingIgnoreCase(Pageable pageable,String name);
}
