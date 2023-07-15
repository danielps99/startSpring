package br.com.bdws.startSpring.service;

import br.com.bdws.startSpring.entity.Product;
import br.com.bdws.startSpring.exception.GenericException;
import br.com.bdws.startSpring.dto.ProductDto;
import br.com.bdws.startSpring.repository.IProductJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private IProductJpaRepository jpaRepository;
    @Autowired
    private ModelMapper mapper;

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