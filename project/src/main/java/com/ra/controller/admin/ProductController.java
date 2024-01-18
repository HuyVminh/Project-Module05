package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.ProductRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.service.product.IProductService;
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
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getallProducts(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int limit,
                                            @RequestParam(name = "sort", defaultValue = "id") String sort,
                                            @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<ProductResponseDTO> products = productService.findAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/search")
    public ResponseEntity<?> searchProducts(@RequestParam(name = "name") String search,
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
        Page<ProductResponseDTO> products = productService.searchByName(pageable, search);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> addProduct(@Valid @ModelAttribute("product") ProductRequestDTO productRequestDTO) throws CustomException {
        ProductResponseDTO productResponseDTO = productService.saveOrUpdate(productRequestDTO, null);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> editProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("product") ProductRequestDTO productRequestDTO) throws CustomException {
        ProductResponseDTO productResponseDTO = productService.saveOrUpdate(productRequestDTO, id);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }
    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> changeStatus(@PathVariable("id") Long id) throws CustomException {
        ProductResponseDTO productResponseDTO = productService.changeStatus(id);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }
}
