package com.ra.service.product;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.ProductRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.entity.Product;
import com.ra.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceIMPL implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductResponseDTO::new);
    }

    @Override
    public Page<ProductResponseDTO> searchByName(Pageable pageable, String name) {
        Page<Product> products = productRepository.findAllByProductNameContainingIgnoreCase(pageable, name);
        return products.map(ProductResponseDTO::new);
    }

    @Override
    public ProductResponseDTO changeStatus(Long id) throws CustomException {
        Product product = productRepository.findById(id).orElseThrow(()->new CustomException("Không tìm thấy sản phẩm !"));
        product.setStatus(!product.getStatus());
        productRepository.save(product);
        return new ProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO saveOrUpdate(ProductRequestDTO productRequestDTO, Long id) throws CustomException {
        Product product;
        if (id == null) {
            if(productRepository.existsProductByProductName(productRequestDTO.getProductName())){
                throw new CustomException("Tên sản phẩm đã tồn tại !");
            }
            product = Product.builder().
                    productName(productRequestDTO.getProductName()).
                    price(Float.valueOf(productRequestDTO.getPrice())).
                    description(productRequestDTO.getDescription()).
                    status(productRequestDTO.getStatus()).
                    build();
        } else {
            product = productRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy sản phẩm !"));
            if(!product.getProductName().equals(productRequestDTO.getProductName())){
                if(productRepository.existsProductByProductName(productRequestDTO.getProductName())){
                    throw new CustomException("Tên sản phẩm đã tồn tại !");
                }
                product.setProductName(productRequestDTO.getProductName());
            }
            product.setPrice(Float.valueOf(productRequestDTO.getPrice()));
            product.setDescription(productRequestDTO.getDescription());
            product.setStatus(productRequestDTO.getStatus());
        }
        productRepository.save(product);
        return new ProductResponseDTO(product);
    }

    @Override
    public Product findById(Long id) throws CustomException {
        return productRepository.findById(id).orElseThrow(()->new CustomException("Không tìm thấy dịch vụ !"));
    }
}
