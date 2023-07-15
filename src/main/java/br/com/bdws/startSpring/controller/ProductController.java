package br.com.bdws.startSpring.controller;

import br.com.bdws.startSpring.dto.ProductDto;
import br.com.bdws.startSpring.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

    @Autowired
    private ProductService service;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/product")
    @ResponseBody
    public Long save(@RequestBody ProductDto dto) {
        return service.save(dto);
    }

    @GetMapping("/product")
    @ResponseBody
    public List<ProductDto> findAll() {
        logger.debug("Logger debug apenas teste");
        logger.info("Logger info apenas teste");
        logger.warn("Logger warn apenas teste");
        return service.findAll();
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    public ProductDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/product/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}