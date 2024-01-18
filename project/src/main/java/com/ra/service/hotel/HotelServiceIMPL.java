package com.ra.service.hotel;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.HotelRequestDTO;
import com.ra.model.dto.response.HotelResponseDTO;
import com.ra.model.entity.City;
import com.ra.model.entity.Hotel;
import com.ra.repository.IHotelRepository;
import com.ra.service.city.ICityService;
import com.ra.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceIMPL implements IHotelService {
    @Autowired
    private IHotelRepository hotelRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ICityService cityService;

    @Override
    public Page<HotelResponseDTO> findAll(Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        return hotels.map(HotelResponseDTO::new);
    }

    @Override
    public Page<HotelResponseDTO> findAllByUser(Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        List<Hotel> hotelList = hotels.getContent();
        return new PageImpl<>(hotelList.stream()
                .filter(Hotel::getStatus)
                .map(HotelResponseDTO::new)
                .collect(Collectors.toList()), pageable, hotels.getTotalElements());
    }

    @Override
    public HotelResponseDTO saveOrUpdate(HotelRequestDTO hotelRequestDTO, Long id) throws CustomException {
        Hotel hotel;
        // gọi đến hàm upload file
        String fileName;
        City cityAdd = cityService.findById(hotelRequestDTO.getCityId());
        if (cityAdd == null) {
            throw new CustomException("Hãy chọn thành phố !");
        }
        if (id == null) {
            fileName = uploadService.uploadImage(hotelRequestDTO.getImage());
            if (hotelRepository.existsHotelByHotelName(hotelRequestDTO.getHotelName())) {
                throw new CustomException("Tên khách sạn này đã tồn tại !");
            }
            hotel = Hotel.builder().
                    hotelName(hotelRequestDTO.getHotelName()).
                    address(hotelRequestDTO.getAddress()).
                    description(hotelRequestDTO.getDescription()).
                    city(cityAdd).
                    image(fileName).
                    rooms(new ArrayList<>()).
                    status(hotelRequestDTO.getStatus()).
                    build();
        } else {
            hotel = hotelRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy khách sạn !!"));
            if (!hotel.getHotelName().equals(hotelRequestDTO.getHotelName())) {
                if (hotelRepository.existsHotelByHotelName(hotelRequestDTO.getHotelName())) {
                    throw new CustomException("Tên khách sạn này đã tồn tại !");
                }
                hotel.setHotelName(hotelRequestDTO.getHotelName());
            }
            if (!hotelRequestDTO.getImage().isEmpty()) {
                fileName = uploadService.uploadImage(hotelRequestDTO.getImage());
                hotel.setImage(fileName);
            }
            hotel.setCity(cityAdd);
            hotel.setAddress(hotelRequestDTO.getAddress());
            hotel.setDescription(hotelRequestDTO.getDescription());
            hotel.setStatus(hotelRequestDTO.getStatus());
        }
        hotelRepository.save(hotel);
        return new HotelResponseDTO(hotel);
    }

    @Override
    public HotelResponseDTO changeStatusHotel(Long hotelId) throws CustomException {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new CustomException("Không tìm thấy khách sạn !"));
        hotel.setStatus(!hotel.getStatus());
        hotelRepository.save(hotel);
        return new HotelResponseDTO(hotel);
    }

    @Override
    public Page<HotelResponseDTO> searchByHotelName(Pageable pageable, String name) {
        Page<Hotel> hotelResponseDTOPage = hotelRepository.findAllByHotelNameContainingIgnoreCase(pageable, name);
        return hotelResponseDTOPage.map(HotelResponseDTO::new);
    }

    @Override
    public Page<HotelResponseDTO> findAllByCity(Pageable pageable, Long cityId) throws CustomException {
        City city = cityService.findById(cityId);
        Page<Hotel> hotels = hotelRepository.findHotelsByCityAndStatus(pageable, city, true);
        return hotels.map(HotelResponseDTO::new);
    }

    @Override
    public Hotel findById(Long id) throws CustomException {
        return hotelRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy Khách sạn !"));
    }
}
