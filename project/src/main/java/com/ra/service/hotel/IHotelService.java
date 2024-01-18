package com.ra.service.hotel;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.HotelRequestDTO;
import com.ra.model.dto.response.HotelResponseDTO;
import com.ra.model.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IHotelService {
    Page<HotelResponseDTO> findAll(Pageable pageable);
    Page<HotelResponseDTO> findAllByUser(Pageable pageable);
    HotelResponseDTO saveOrUpdate(HotelRequestDTO hotelRequestDTO,Long id) throws CustomException;
    HotelResponseDTO changeStatusHotel(Long hotelId) throws CustomException;
    Page<HotelResponseDTO> searchByHotelName(Pageable pageable,String name);
    Page<HotelResponseDTO> findAllByCity(Pageable pageable,Long cityId) throws CustomException;
    Hotel findById(Long id) throws CustomException;
}
