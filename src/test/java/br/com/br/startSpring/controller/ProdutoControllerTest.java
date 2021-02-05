package br.com.br.startSpring.controller;

import br.com.br.startSpring.dto.ProdutoDto;
import br.com.br.startSpring.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoControllerTest {

    @Autowired
    private ProdutoController produtoController;

    @Test
    void criarProduto() {
        ProdutoDto produtoNovo = instanciarProduto();
        Long idSalvo = produtoController.save(produtoNovo);
        ProdutoDto recuperado = produtoController.findById(idSalvo);
        assertEquals(produtoNovo.getSku(), recuperado.getSku());
        assertEquals(produtoNovo.getDescricao(), recuperado.getDescricao());
        assertEquals(produtoNovo.getUnidade(), recuperado.getUnidade());
        assertEquals(produtoNovo.getUnidadeMedida(), recuperado.getUnidadeMedida());
    }

    @Test
    void recuperarProduto() {
        ProdutoDto produtoNovo = instanciarProduto();
        Long idSalvo = produtoController.save(produtoNovo);
        ProdutoDto recuperado = produtoController.findById(idSalvo);

        assertEquals(recuperado.getSku(), "1");
        assertEquals(recuperado.getDescricao(), "Primeiro produto");
        assertEquals(recuperado.getUnidadeMedida(), "METRO");
        assertEquals(recuperado.getUnidade(), BigDecimal.valueOf(10.12));
    }

    @Test
    void lancarExceptionAoRecuperarIdInexistente() {
        try {
            produtoController.findById(99L);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Produto não encontrado.");
        }
    }

    @Test
    void alterarProduto() {
        ProdutoDto produtoNovo = instanciarProduto();
        Long idSalvo = produtoController.save(produtoNovo);

        ProdutoDto recuperado = produtoController.findById(idSalvo);
        recuperado.setDescricao("Descrição modificada");
        produtoController.save(recuperado);
        ProdutoDto editadoRecuperado = produtoController.findById(idSalvo);
        assertEquals(editadoRecuperado.getDescricao(), "Descrição modificada");
    }

    @Test
    void lancarExceptionAoCriarProdutoNulo() {
        try {
            produtoController.save(null);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Erro ao salvar produto.");
        }
    }

    @Test
    void removerProduto() {
        ProdutoDto produtoNovo = instanciarProduto();
        Long idSalvo = produtoController.save(produtoNovo);

        ProdutoDto recuperadoAntesDeletar = produtoController.findById(idSalvo);
        assertNotNull(recuperadoAntesDeletar);

        produtoController.delete(idSalvo);

        ProdutoDto tentativaRecuperar = null;
        try {
            tentativaRecuperar = produtoController.findById(idSalvo);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Produto não encontrado.");
        }
        assertNull(tentativaRecuperar);
    }

    @Test
    void lancarExceptionAoRemoverProdutoInexistente() {
        try {
            produtoController.delete(99L);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Produto não encontrado.");
        }
    }

    private ProdutoDto instanciarProduto() {
        return ProdutoDto.builder()
                .sku("1")
                .descricao("Primeiro produto")
                .unidadeMedida("METRO")
                .unidade(BigDecimal.valueOf(10.12))
                .build();
    }
}