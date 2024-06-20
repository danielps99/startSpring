package br.com.bdws.start_spring.controller;

import br.com.bdws.start_spring.dto.ProductDto;
import br.com.bdws.start_spring.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Tag(name = "Products", description = "Controller used to save, list, get and delete product.")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/product")
    @Operation(
            summary =  "Create or update a product.",
            description = "Create or update a product. To create, remove id field.")
    public Long save(@RequestBody ProductDto dto) {
        return service.save(dto);
    }

    @GetMapping("/product")
    @Operation(
            summary =  "Fetch all products in the database.",
            description = "Fetch all products in the database. No filter parameter is allowed.")
    public List<ProductDto> findAll() {
        logger.debug("Logger debug apenas teste");
        logger.info("Logger info apenas teste");
        logger.warn("Logger warn apenas teste");
        return service.findAll();
    }

    @GetMapping("/product/{id}")
    @Operation(
            summary =  "Fetch a specific product by id.",
            description = "Fetch a specific product by id. If not found, will response status 500")
    public ProductDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/product/{id}")
    @Operation(
            summary =  "Delete a specific product by id.",
            description = "Delete a specific product by id. If not found, will response status 500")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}