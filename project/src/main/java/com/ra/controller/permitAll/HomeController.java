package com.ra.controller.permitAll;

import com.ra.exception.CustomException;
import com.ra.model.dto.response.CityResponseDTO;
import com.ra.model.dto.response.HotelResponseDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.dto.response.RoomResponseDTO;
import com.ra.model.entity.Hotel;
import com.ra.service.city.ICityService;
import com.ra.service.hotel.IHotelService;
import com.ra.service.product.IProductService;
import com.ra.service.room.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1")
public class HomeController {
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IProductService productService;

    @GetMapping("/cities")
    public ResponseEntity<?> getCities(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int limit,
                                       @RequestParam(name = "sort", defaultValue = "cityName") String sort,
                                       @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<CityResponseDTO> cities = cityService.findAllByUser(pageable);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<?> getHotelsByCity(@PathVariable("id") Long id,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int limit,
                                             @RequestParam(name = "sort", defaultValue = "status") String sort,
                                             @RequestParam(name = "order", defaultValue = "desc") String order) throws CustomException {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<HotelResponseDTO> list = hotelService.findAllByCity(pageable, id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/hotels")
    public ResponseEntity<?> getHotels(@RequestParam(name = "search")String search,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int limit,
                                       @RequestParam(name = "sort", defaultValue = "status") String sort,
                                       @RequestParam(name = "order", defaultValue = "desc") String order) throws CustomException {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<HotelResponseDTO> list;
        if (search.isEmpty()){
            list = hotelService.findAllByUser(pageable);
        }else {
            list = hotelService.searchByHotelName(pageable,search);
            if (list.isEmpty()){
                throw new CustomException("Không tìm thấy khách sạn !");
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelResponseDTO> hotelDetail(@PathVariable("id") Long id) throws CustomException {
        Hotel hotel = hotelService.findById(id);
        if (!hotel.getStatus()){
            throw new CustomException("Không thể truy cập khách sạn !");
        }
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO(hotel);
        return new ResponseEntity<>(hotelResponseDTO, HttpStatus.OK);
    }
    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int limit,
                                      @RequestParam(name = "sort", defaultValue = "price") String sort,
                                      @RequestParam(name = "order", defaultValue = "desc") String order){
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<RoomResponseDTO> list = roomService.findAllByUser(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/types/{id}")
    public ResponseEntity<?> getRoomsByType(@PathVariable("id") Long id,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int limit) throws CustomException {
        Pageable pageable = PageRequest.of(page, limit);
        Page<RoomResponseDTO> list = roomService.searchByTypeId(pageable,id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(page, limit);
        Page<ProductResponseDTO> list = productService.findAll(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
