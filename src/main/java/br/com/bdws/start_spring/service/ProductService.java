package br.com.bdws.start_spring.service;

import br.com.bdws.start_spring.dto.ProductDto;
import br.com.bdws.start_spring.entity.Product;
import br.com.bdws.start_spring.exception.GenericException;
import br.com.bdws.start_spring.repository.IProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductJpaRepository jpaRepository;
    private final ModelMapper mapper;

    public ProductDto findById(Long id) {
        Product product = jpaRepository.findById(id).orElseThrow(
                () -> new GenericException("Product not found.")
        );
        return mapper.map(product, ProductDto.class);
    }

    public List<ProductDto> findAll() {
        return jpaRepository.findAll().stream()
                .map(book -> mapper.map(book, ProductDto.class)).collect(Collectors.toList());
    }

    public Long save(ProductDto dto) {
        try {
            Product product = mapper.map(dto, Product.class);
            Product salvo = jpaRepository.save(product);
            return salvo.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException("Error saving product.");
        }
    }

    public void deleteById(Long id) {
        try {
            jpaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new GenericException("Product not found.");
        }
    }
}