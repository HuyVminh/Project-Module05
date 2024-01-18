package com.ra.service.room_type;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.RoomTypeRequestDTO;
import com.ra.model.dto.response.RoomTypeResponseDTO;
import com.ra.model.entity.RoomType;
import com.ra.repository.ITypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeServiceIMPL implements IRoomTypeService {
    @Autowired
    private ITypeRepository typeRepository;

    @Override
    public Page<RoomTypeResponseDTO> findAll(Pageable pageable) {
        Page<RoomType> roomTypes = typeRepository.findAll(pageable);
        return roomTypes.map(RoomTypeResponseDTO::new);
    }

    @Override
    public RoomTypeResponseDTO saveOrUpdate(RoomTypeRequestDTO roomTypeRequestDTO, Long id) throws CustomException {
        RoomType roomType;
        if (id == null) {
            if(typeRepository.existsRoomTypeByType(roomTypeRequestDTO.getType())){
                throw new CustomException("Type Room đã tồn tại !");
            }
            roomType = RoomType.builder().
                    type(roomTypeRequestDTO.getType()).
                    description(roomTypeRequestDTO.getDescription()).
                    status(roomTypeRequestDTO.getStatus()).
                    build();
        } else {
            roomType = typeRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy TypeRoom !"));
            if (!roomType.getType().equals(roomTypeRequestDTO.getType())){
                if(typeRepository.existsRoomTypeByType(roomTypeRequestDTO.getType())){
                    throw new CustomException("Type Room đã tồn tại !");
                }
                roomType.setType(roomTypeRequestDTO.getType());
            }
            roomType.setStatus(roomTypeRequestDTO.getStatus());
            roomType.setDescription(roomTypeRequestDTO.getDescription());
        }
        typeRepository.save(roomType);
        return new RoomTypeResponseDTO(roomType);
    }

    @Override
    public Page<RoomTypeResponseDTO> searchByName(Pageable pageable, String name) {
        Page<RoomType> roomTypes = typeRepository.findAllByTypeContainingIgnoreCase(pageable, name);
        return roomTypes.map(RoomTypeResponseDTO::new);
    }

    @Override
    public RoomTypeResponseDTO changeStatusById(Long id) throws CustomException {
        RoomType roomType = typeRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy TypeRoom !"));
        roomType.setStatus(!roomType.getStatus());
        typeRepository.save(roomType);
        return new RoomTypeResponseDTO(roomType);
    }

    @Override
    public RoomType findById(Long id) throws CustomException {
        return typeRepository.findById(id).orElseThrow(() -> new CustomException("Không tồn tại kiểu phòng này !"));
    }
}
