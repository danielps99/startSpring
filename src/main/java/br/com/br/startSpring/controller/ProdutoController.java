package br.com.br.startSpring.controller;

import br.com.br.startSpring.entity.Produto;
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
    public Long save(@RequestBody Produto produto) {
        return service.salvar(produto);
    }

    @GetMapping("/produto")
    @ResponseBody
    public List<Produto> findAll() {
        return service.findAll();
    }

    @GetMapping("/produto/{id}")
    @ResponseBody
    public Produto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/produto/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        service.removerById(id);
    }
}