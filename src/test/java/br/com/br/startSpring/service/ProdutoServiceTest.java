package br.com.br.startSpring.service;

import br.com.br.startSpring.entity.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Test
    void contextLoads() {
        Produto p = new Produto(1L, "1", "Primeiro produto", "METRO", BigDecimal.valueOf(10.12));
        Produto salvo = produtoService.salvar(p);
        Produto recuperado = produtoService.findById(1L);
        Assertions.assertEquals(salvo, recuperado);
    }
}