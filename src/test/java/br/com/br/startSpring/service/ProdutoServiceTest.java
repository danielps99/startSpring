package br.com.br.startSpring.service;

import br.com.br.startSpring.entity.Produto;
import br.com.br.startSpring.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Test
    void criarProduto() {
        Produto salvo = produtoService.salvar(instanciarProduto());
        Produto recuperado = produtoService.findById(salvo.getId());
        assertEquals(salvo, recuperado);
    }

    @Test
    void recuperarProduto() {
        Produto salvo = produtoService.salvar(instanciarProduto());
        Produto recuperado = produtoService.findById(salvo.getId());

        assertEquals(recuperado.getSku(), "1");
        assertEquals(recuperado.getDescricao(), "Primeiro produto");
        assertEquals(recuperado.getUnidadeMedida(), "METRO");
        assertEquals(recuperado.getUnidade(), BigDecimal.valueOf(10.12));
    }

    @Test
    void lancarExceptionAoRecuperarIdInexistente() {
        try {
            produtoService.findById(99L);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Produto não encontrado.");
        }
    }

    @Test
    void alterarProduto() {
        Produto salvo = produtoService.salvar(instanciarProduto());
        Produto recuperado = produtoService.findById(salvo.getId());
        recuperado.setDescricao("Descrição modificada");
        Produto editado = produtoService.salvar(recuperado);
        assertEquals(editado.getDescricao(), "Descrição modificada");
    }

    @Test
    void lancarExceptionAoCriarProdutoNulo() {
        try {
            produtoService.salvar(null);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Erro ao salvar produto.");
        }
    }

    @Test
    void removerProduto() {
        Produto salvo = produtoService.salvar(instanciarProduto());
        Produto recuperadoAntesDeletar = produtoService.findById(salvo.getId());
        assertNotNull(recuperadoAntesDeletar);

        produtoService.removerById(salvo.getId());

        Produto tentativaRecuperar = null;
        try {
            tentativaRecuperar = produtoService.findById(salvo.getId());
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Produto não encontrado.");
        }
        assertNull(tentativaRecuperar);
    }

    @Test
    void lancarExceptionAoRemoverProdutoInexistente() {
        try {
            produtoService.removerById(99L);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Produto não encontrado.");
        }
    }

    private Produto instanciarProduto() {
        return new Produto(null, "1", "Primeiro produto", "METRO", BigDecimal.valueOf(10.12));
    }
}