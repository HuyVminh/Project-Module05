package com.ra.repository;

import com.ra.model.entity.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeRepository extends JpaRepository<RoomType, Long> {
    Page<RoomType> findAllByTypeContainingIgnoreCase(Pageable pageable,String name);
    Boolean existsRoomTypeByType(String name);
}
