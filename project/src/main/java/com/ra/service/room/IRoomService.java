package com.ra.service.room;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.RoomRequestDTO;
import com.ra.model.dto.response.RoomResponseDTO;
import com.ra.model.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoomService {
    Page<RoomResponseDTO> findAll(Pageable pageable);
    Page<RoomResponseDTO> findAllByUser(Pageable pageable);
    Page<RoomResponseDTO> searchByType(Pageable pageable, String type);
    Page<RoomResponseDTO> searchByTypeId(Pageable pageable, Long id) throws CustomException;
    RoomResponseDTO saveOrUpdate(RoomRequestDTO roomRequestDTO, Long id) throws CustomException;
    RoomResponseDTO changeStatus(Long id) throws CustomException;
    Room findById(Long id) throws CustomException;
}
