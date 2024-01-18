package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.CityRequestDTO;
import com.ra.model.dto.request.HotelRequestDTO;
import com.ra.model.dto.response.CityResponseDTO;
import com.ra.model.dto.response.HotelResponseDTO;
import com.ra.model.entity.Hotel;
import com.ra.service.hotel.IHotelService;
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
public class HotelController {
    @Autowired
    private IHotelService hotelService;
    @GetMapping("/hotels")
    public ResponseEntity<?> getHotels(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int limit,
                                       @RequestParam(name = "sort",defaultValue = "id") String sort,
                                       @RequestParam(name = "order",defaultValue = "desc") String order){
        Pageable pageable;
        if(order.equals("desc")){
            pageable = PageRequest.of(page,limit, Sort.by(sort).descending());
        }else {
            pageable = PageRequest.of(page,limit, Sort.by(sort).ascending());
        }
        Page<HotelResponseDTO> hotels = hotelService.findAll(pageable);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
    @GetMapping("/hotels/search")
    public ResponseEntity<?> searchHotelByName(@RequestParam(name = "name") String search,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int limit,
                                              @RequestParam(name = "sort", defaultValue = "id") String sort,
                                              @RequestParam(name = "order", defaultValue = "desc") String order) throws CustomException {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<HotelResponseDTO> hotels = hotelService.searchByHotelName(pageable, search);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelResponseDTO> saveCity(@Valid @ModelAttribute("hotel") HotelRequestDTO hotelRequestDTO) throws CustomException {
        HotelResponseDTO hotelResponseDTO = hotelService.saveOrUpdate(hotelRequestDTO, null);
        return new ResponseEntity<>(hotelResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<HotelResponseDTO> updateCity(@PathVariable("id") Long id,
                                                       @Valid @ModelAttribute("hotel") HotelRequestDTO hotelRequestDTO) throws CustomException {
        HotelResponseDTO hotelResponseDTO = hotelService.saveOrUpdate(hotelRequestDTO, id);
        return new ResponseEntity<>(hotelResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/hotels/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id) throws CustomException {
        HotelResponseDTO hotelResponseDTO = hotelService.changeStatusHotel(id);
        return new ResponseEntity<>(hotelResponseDTO, HttpStatus.OK);
    }
}
