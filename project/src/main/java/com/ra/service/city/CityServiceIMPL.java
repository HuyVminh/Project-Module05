package com.ra.service.city;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.CityRequestDTO;
import com.ra.model.dto.response.CityResponseDTO;
import com.ra.model.entity.City;
import com.ra.repository.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityServiceIMPL implements ICityService {
    @Autowired
    private ICityRepository cityRepository;

    @Override
    public Page<CityResponseDTO> findAll(Pageable pageable) {
        Page<City> cities = cityRepository.findAll(pageable);
        return cities.map(CityResponseDTO::new);
    }

    @Override
    public Page<CityResponseDTO> findAllByUser(Pageable pageable) {
        Page<City> cities = cityRepository.findAll(pageable);
        return (Page<CityResponseDTO>) cities.
                filter(City::getStatus).
                map(CityResponseDTO::new);
    }

    @Override
    public CityResponseDTO saveOrUpdate(CityRequestDTO cityRequestDTO, Long id) throws CustomException {

        City city;
        if (id == null) {
            if (cityRepository.existsByCityName(cityRequestDTO.getCityName())) {
                throw new CustomException("Tên thành phố đã tồn tại !");
            }
            city = City.builder().
                    cityName(cityRequestDTO.getCityName()).
                    status(cityRequestDTO.getStatus()).
                    build();
        } else {
            city = cityRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy CITY !"));
            if(!city.getCityName().equals(cityRequestDTO.getCityName())){
                if (cityRepository.existsByCityName(cityRequestDTO.getCityName())) {
                    throw new CustomException("Tên thành phố đã tồn tại !");
                }
                city.setCityName(cityRequestDTO.getCityName());
            }
            city.setStatus(cityRequestDTO.getStatus());
        }
        cityRepository.save(city);
        return new CityResponseDTO(city);
    }

    @Override
    public CityResponseDTO changeStatusCity(Long cityId) throws CustomException {
        City city = cityRepository.findById(cityId).orElse(null);
        if (city == null) {
            throw new CustomException("Thành phố không tồn tại !");
        } else {
            city.setStatus(!city.getStatus());
            cityRepository.save(city);
            return new CityResponseDTO(city);
        }
    }

    @Override
    public Page<CityResponseDTO> searchByName(Pageable pageable, String name) throws CustomException {
        Page<City> cities;
        if (name.isEmpty()) {
            cities = cityRepository.findAll(pageable);
        } else {
            cities = cityRepository.findAllByCityNameContainingIgnoreCase(pageable, name);
            if (cities.isEmpty()) {
                throw new CustomException("Không tìm thấy thành phố nào !");
            }
        }
        return cities.map(CityResponseDTO::new);
    }

    @Override
    public City findById(Long id) throws CustomException {
        return cityRepository.findById(id).orElseThrow(()->new CustomException("Không tìm thấy thành phố !"));
    }
}
