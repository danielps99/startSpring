package br.com.br.startSpring.controller;

import br.com.br.startSpring.dto.ProdutoDto;
import br.com.br.startSpring.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping("/produto")
    @ResponseBody
    public Long save(@RequestBody ProdutoDto dto) {
        return service.salvar(dto);
    }

    @GetMapping("/produto")
    @ResponseBody
    public List<ProdutoDto> findAll() {
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
        service.removerById(id);
    }
}