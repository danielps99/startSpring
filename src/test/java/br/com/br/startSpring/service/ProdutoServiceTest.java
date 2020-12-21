package br.com.br.startSpring.service;

import br.com.br.startSpring.entity.Produto;
import br.com.br.startSpring.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Test
    void criarProduto() {
        Produto produtoNovo = instanciarProduto();
        Long idSalvo = produtoService.salvar(produtoNovo);
        Produto recuperado = produtoService.findById(idSalvo);
        assertEquals(produtoNovo, recuperado);
    }

    @Test
    void recuperarProduto() {
        Produto produtoNovo = instanciarProduto();
        Long idSalvo = produtoService.salvar(produtoNovo);
        Produto recuperado = produtoService.findById(idSalvo);

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
        Produto produtoNovo = instanciarProduto();
        Long idSalvo = produtoService.salvar(produtoNovo);

        Produto recuperado = produtoService.findById(idSalvo);
        recuperado.setDescricao("Descrição modificada");
        produtoService.salvar(recuperado);
        Produto editadoRecuperado = produtoService.findById(idSalvo);
        assertEquals(editadoRecuperado.getDescricao(), "Descrição modificada");
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
        Produto produtoNovo = instanciarProduto();
        Long idSalvo = produtoService.salvar(produtoNovo);

        Produto recuperadoAntesDeletar = produtoService.findById(idSalvo);
        assertNotNull(recuperadoAntesDeletar);

        produtoService.removerById(idSalvo);

        Produto tentativaRecuperar = null;
        try {
            tentativaRecuperar = produtoService.findById(idSalvo);
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
        return Produto.builder()
                .sku("1")
                .descricao("Primeiro produto")
                .unidadeMedida("METRO")
                .unidade(BigDecimal.valueOf(10.12))
                .build();
    }
}