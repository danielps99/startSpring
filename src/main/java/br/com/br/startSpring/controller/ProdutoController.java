package br.com.br.startSpring.controller;

import br.com.br.startSpring.dto.ProdutoDto;
import br.com.br.startSpring.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ProdutoController {

    @Autowired
    private ProdutoService service;
    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @PostMapping("/produto")
    @ResponseBody
    public Long save(@RequestBody ProdutoDto dto) {
        return service.save(dto);
    }

    @GetMapping("/produto")
    @ResponseBody
    public List<ProdutoDto> findAll() {
        logger.debug("Logger debug apenas teste");
        logger.info("Logger info apenas teste");
        logger.warn("Logger warn apenas teste");
        return service.findAll();
    }

    @GetMapping("/produto/{id}")
    @ResponseBody
    public ProdutoDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/produto/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}