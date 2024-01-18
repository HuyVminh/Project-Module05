package com.ra.service.product;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.ProductRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<ProductResponseDTO> findAll(Pageable pageable);
    Page<ProductResponseDTO> searchByName(Pageable pageable, String name);
    ProductResponseDTO changeStatus(Long id) throws CustomException;
    ProductResponseDTO saveOrUpdate(ProductRequestDTO productRequestDTO,Long id) throws CustomException;
    Product findById(Long id) throws CustomException;
}
