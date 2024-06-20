package br.com.bdws.start_spring.controller;

import br.com.bdws.start_spring.dto.ProductDto;
import br.com.bdws.start_spring.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    private ProductDto buildProduct() {
        return ProductDto.builder()
                .sku("1")
                .description("First product")
                .measurementUnit("METER")
                .unitPrice(BigDecimal.valueOf(10.12))
                .build();
    }

    @Test
    void criarProduto() {
        ProductDto newProduct = buildProduct();
        Long savedId = productController.save(newProduct);
        ProductDto recovered = productController.findById(savedId);
        assertEquals(newProduct.getSku(), recovered.getSku());
        assertEquals(newProduct.getDescription(), recovered.getDescription());
        assertEquals(newProduct.getUnitPrice(), recovered.getUnitPrice());
        assertEquals(newProduct.getMeasurementUnit(), recovered.getMeasurementUnit());
    }

    @Test
    void recuperarProduto() {
        ProductDto newProduct = buildProduct();
        Long savedId = productController.save(newProduct);
        ProductDto recovered = productController.findById(savedId);

        assertEquals(recovered.getSku(), "1");
        assertEquals(recovered.getDescription(), "First product");
        assertEquals(recovered.getMeasurementUnit(), "METER");
        assertEquals(recovered.getUnitPrice(), BigDecimal.valueOf(10.12));
    }

    @Test
    void lancarExceptionAoRecuperarIdInexistente() {
        try {
            productController.findById(99L);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Product not found.");
        }
    }

    @Test
    void alterarProduto() {
        ProductDto newProduct = buildProduct();
        Long savedId = productController.save(newProduct);

        ProductDto recovered = productController.findById(savedId);
        recovered.setDescription("Modified description");
        productController.save(recovered);
        ProductDto recoveredEdited = productController.findById(savedId);
        assertEquals(recoveredEdited.getDescription(), "Modified description");
    }

    @Test
    void lancarExceptionAoCriarProdutoNulo() {
        try {
            productController.save(null);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Error saving product.");
        }
    }

    @Test
    void removerProduto() {
        ProductDto newProduct = buildProduct();
        Long savedId = productController.save(newProduct);

        ProductDto recoveredBeforeDelete = productController.findById(savedId);
        assertNotNull(recoveredBeforeDelete);

        productController.delete(savedId);

        ProductDto attemptRecover = null;
        try {
            attemptRecover = productController.findById(savedId);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Product not found.");
        }
        assertNull(attemptRecover);
    }

    @Test
    void lancarExceptionAoRemoverProdutoInexistente() {
        try {
            productController.delete(99L);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals(e.getMessage(), "Product not found.");
        }
    }
}