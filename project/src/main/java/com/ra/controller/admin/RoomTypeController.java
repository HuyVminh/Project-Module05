package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.RoomTypeRequestDTO;
import com.ra.model.dto.response.RoomTypeResponseDTO;
import com.ra.service.room_type.IRoomTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class RoomTypeController {
    @Autowired
    private IRoomTypeService roomTypeService;

    @GetMapping("/room-types")
    public ResponseEntity<?> getRoomTypes(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int limit,
                                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                                          @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<RoomTypeResponseDTO> roomTypeResponseDTOS = roomTypeService.findAll(pageable);
        return new ResponseEntity<>(roomTypeResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/room-types/search")
    public ResponseEntity<?> searchTypes(@RequestParam(name = "name") String search,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int limit,
                                         @RequestParam(name = "sort", defaultValue = "id") String sort,
                                         @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<RoomTypeResponseDTO> roomTypeResponseDTOS = roomTypeService.searchByName(pageable, search);
        return new ResponseEntity<>(roomTypeResponseDTOS, HttpStatus.OK);
    }

    @PostMapping("/room-types")
    public ResponseEntity<RoomTypeResponseDTO> addNewRoomType(@RequestBody @Valid RoomTypeRequestDTO roomTypeRequestDTO) throws CustomException {
        RoomTypeResponseDTO roomTypeResponseDTO = roomTypeService.saveOrUpdate(roomTypeRequestDTO, null);
        return new ResponseEntity<>(roomTypeResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/room-types/{id}")
    public ResponseEntity<RoomTypeResponseDTO> updateRoomType(@PathVariable("id") Long id,
                                                              @RequestBody @Valid RoomTypeRequestDTO roomTypeRequestDTO) throws CustomException {
        RoomTypeResponseDTO roomTypeResponseDTO = roomTypeService.saveOrUpdate(roomTypeRequestDTO, id);
        return new ResponseEntity<>(roomTypeResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/room-types/{id}")
    public ResponseEntity<RoomTypeResponseDTO> updateStatus(@PathVariable("id") Long id) throws CustomException {
        RoomTypeResponseDTO roomTypeResponseDTO = roomTypeService.changeStatusById(id);
        return new ResponseEntity<>(roomTypeResponseDTO, HttpStatus.OK);
    }
}
