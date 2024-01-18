package com.ra.service.city;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.CityRequestDTO;
import com.ra.model.dto.response.CityResponseDTO;
import com.ra.model.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICityService {
    Page<CityResponseDTO> findAll(Pageable pageable);
    Page<CityResponseDTO> findAllByUser(Pageable pageable);

    CityResponseDTO saveOrUpdate(CityRequestDTO cityRequestDTO,Long id) throws CustomException;
    CityResponseDTO changeStatusCity(Long cityId) throws CustomException;
    Page<CityResponseDTO> searchByName(Pageable pageable,String name) throws CustomException;
    City findById(Long id) throws CustomException;
}
