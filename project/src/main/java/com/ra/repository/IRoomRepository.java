package com.ra.repository;

import com.ra.model.dto.response.RoomResponseDTO;
import com.ra.model.entity.Room;
import com.ra.model.entity.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomRepository extends JpaRepository<Room, Long> {
    Boolean existsRoomByRoomNo(String room);

    Page<Room> searchRoomsByRoomTypeContainingIgnoreCase(Pageable pageable, String type);

    Page<Room> searchRoomsByRoomType(Pageable pageable, RoomType type);

    Page<Room> findAllByStatus(Pageable pageable, Boolean status);
}
