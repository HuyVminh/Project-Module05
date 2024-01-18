package com.ra.service.room;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.RoomRequestDTO;
import com.ra.model.dto.response.RoomResponseDTO;
import com.ra.model.entity.Hotel;
import com.ra.model.entity.Room;
import com.ra.model.entity.RoomType;
import com.ra.repository.IRoomRepository;
import com.ra.service.hotel.IHotelService;
import com.ra.service.room_type.IRoomTypeService;
import com.ra.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceIMPL implements IRoomService {
    @Autowired
    private IRoomRepository roomRepository;
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private IRoomTypeService roomTypeService;

    @Override
    public Page<RoomResponseDTO> findAll(Pageable pageable) {
        Page<Room> rooms = roomRepository.findAll(pageable);
        return rooms.map(RoomResponseDTO::new);
    }

    @Override
    public Page<RoomResponseDTO> findAllByUser(Pageable pageable) {
        Page<Room> rooms = roomRepository.findAllByStatus(pageable, true);
        return rooms.map(RoomResponseDTO::new);
    }

    @Override
    public Page<RoomResponseDTO> searchByType(Pageable pageable, String type) {
        Page<Room> rooms = roomRepository.searchRoomsByRoomTypeContainingIgnoreCase(pageable, type);
        return rooms.map(RoomResponseDTO::new);
    }

    @Override
    public Page<RoomResponseDTO> searchByTypeId(Pageable pageable, Long id) throws CustomException {
        RoomType type = roomTypeService.findById(id);
        Page<Room> rooms = roomRepository.searchRoomsByRoomType(pageable, type);
        return rooms.map(RoomResponseDTO::new);
    }

    @Override
    public RoomResponseDTO saveOrUpdate(RoomRequestDTO roomRequestDTO, Long id) throws CustomException {
        Room room;
        String fileName;
        if (id == null) {
            if (roomRepository.existsRoomByRoomNo(roomRequestDTO.getRoomNo())) {
                throw new CustomException("Số phòng này đã tồn tại !");
            }
            fileName = uploadService.uploadImage(roomRequestDTO.getImage());
            Hotel hotel = hotelService.findById(roomRequestDTO.getHotelId());
            RoomType type = roomTypeService.findById(roomRequestDTO.getTypeId());
            room = Room.builder().
                    roomNo(roomRequestDTO.getRoomNo()).
                    image(fileName).
                    description(roomRequestDTO.getDescription()).
                    price(Float.valueOf(roomRequestDTO.getPrice())).
                    roomType(type).
                    hotel(hotel).
                    status(roomRequestDTO.getStatus()).
                    build();
        } else {
            room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Không tồn tại phòng này !"));
            if (!room.getRoomNo().equals(roomRequestDTO.getRoomNo())) {
                if (roomRepository.existsRoomByRoomNo(roomRequestDTO.getRoomNo())) {
                    throw new CustomException("Số phòng này đã tồn tại !");
                }
                room.setRoomNo(roomRequestDTO.getRoomNo());
            }
            if (!roomRequestDTO.getImage().isEmpty()) {
                fileName = uploadService.uploadImage(roomRequestDTO.getImage());
                room.setImage(fileName);
            }
            RoomType type = roomTypeService.findById(roomRequestDTO.getTypeId());
            room.setRoomType(type);
            Hotel hotel = hotelService.findById(roomRequestDTO.getHotelId());
            room.setHotel(hotel);
            room.setDescription(roomRequestDTO.getDescription());
            room.setPrice(Float.valueOf(roomRequestDTO.getPrice()));
            room.setStatus(roomRequestDTO.getStatus());
        }
        roomRepository.save(room);
        return new RoomResponseDTO(room);
    }

    @Override
    public RoomResponseDTO changeStatus(Long id) throws CustomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy phòng này !"));
        room.setStatus(!room.getStatus());
        roomRepository.save(room);
        return new RoomResponseDTO(room);
    }

    @Override
    public Room findById(Long id) throws CustomException {
        return roomRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy phòng !"));
    }
}
