package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.CityRequestDTO;
import com.ra.model.dto.response.CityResponseDTO;
import com.ra.service.city.ICityService;
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
public class CityController {
    @Autowired
    private ICityService cityService;

    @GetMapping("/cities")
    public ResponseEntity<?> getCities(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int limit,
                                       @RequestParam(name = "sort", defaultValue = "id") String sort,
                                       @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<CityResponseDTO> cities = cityService.findAll(pageable);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/cities/search")
    public ResponseEntity<?> searchCityByName(@RequestParam(name = "search") String search,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int limit,
                                              @RequestParam(name = "sort", defaultValue = "id") String sort,
                                              @RequestParam(name = "order", defaultValue = "desc") String order) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<CityResponseDTO> cities = cityService.searchByName(pageable, search);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PostMapping("/cities")
    public ResponseEntity<CityResponseDTO> saveCity(@RequestBody @Valid CityRequestDTO cityRequestDTO) throws CustomException {
        CityResponseDTO cityResponseDTO = cityService.saveOrUpdate(cityRequestDTO, null);
        return new ResponseEntity<>(cityResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<CityResponseDTO> updateCity(@PathVariable("id") Long id, @RequestBody @Valid CityRequestDTO cityRequestDTO) throws CustomException {
        CityResponseDTO cityResponseDTO = cityService.saveOrUpdate(cityRequestDTO, id);
        return new ResponseEntity<>(cityResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/cities/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id) throws CustomException {
        CityResponseDTO cityResponseDTO = cityService.changeStatusCity(id);
        return new ResponseEntity<>(cityResponseDTO, HttpStatus.OK);
    }
}
