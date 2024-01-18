package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.RoomRequestDTO;
import com.ra.model.dto.response.RoomResponseDTO;
import com.ra.service.room.IRoomService;
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
public class RoomController {
    @Autowired
    private IRoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<?> getAllRooms(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int limit,
                                         @RequestParam(name = "sort", defaultValue = "id") String sort,
                                         @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<RoomResponseDTO> rooms = roomService.findAll(pageable);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/rooms/search")
    public ResponseEntity<?> searchRoomByType(@RequestParam(name = "search") String search,
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
        Page<RoomResponseDTO> rooms = roomService.searchByType(pageable, search);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("/rooms")
    public ResponseEntity<RoomResponseDTO> addNewRoom(@Valid @ModelAttribute("room") RoomRequestDTO roomRequestDTO) throws CustomException {
        RoomResponseDTO room = roomService.saveOrUpdate(roomRequestDTO, null);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<RoomResponseDTO> updateRoom(@Valid @PathVariable("id") Long id, @ModelAttribute("room") RoomRequestDTO roomRequestDTO) throws CustomException {
        RoomResponseDTO room = roomService.saveOrUpdate(roomRequestDTO, id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PatchMapping("/rooms/{id}")
    public ResponseEntity<RoomResponseDTO> changeStatus(@PathVariable("id") Long id) throws CustomException {
        RoomResponseDTO room = roomService.changeStatus(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
