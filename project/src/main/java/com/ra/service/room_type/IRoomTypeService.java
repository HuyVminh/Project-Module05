package com.ra.service.room_type;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.RoomTypeRequestDTO;
import com.ra.model.dto.response.RoomTypeResponseDTO;
import com.ra.model.entity.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoomTypeService {
    Page<RoomTypeResponseDTO> findAll(Pageable pageable);
    RoomTypeResponseDTO saveOrUpdate(RoomTypeRequestDTO roomTypeRequestDTO,Long id) throws CustomException;
    Page<RoomTypeResponseDTO> searchByName(Pageable pageable, String name);
    RoomTypeResponseDTO changeStatusById(Long id) throws CustomException;
    RoomType findById(Long id) throws CustomException;
}
