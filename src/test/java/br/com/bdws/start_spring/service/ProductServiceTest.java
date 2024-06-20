package br.com.bdws.start_spring.service;

import br.com.bdws.start_spring.exception.GenericException;
import br.com.bdws.start_spring.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

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
        Long savedId = productService.save(newProduct);
        ProductDto recovered = productService.findById(savedId);
        assertEquals(newProduct.getSku(), recovered.getSku());
        assertEquals(newProduct.getDescription(), recovered.getDescription());
        assertEquals(newProduct.getUnitPrice(), recovered.getUnitPrice());
        assertEquals(newProduct.getMeasurementUnit(), recovered.getMeasurementUnit());
    }

    @Test
    void recuperarProduto() {
        ProductDto newProduct = buildProduct();
        Long savedId = productService.save(newProduct);
        ProductDto recovered = productService.findById(savedId);

        assertEquals("1", recovered.getSku());
        assertEquals("First product", recovered.getDescription());
        assertEquals("METER", recovered.getMeasurementUnit());
        assertEquals(BigDecimal.valueOf(10.12), recovered.getUnitPrice());
    }

    @Test
    void lancarExceptionAoRecuperarIdInexistente() {
        try {
            productService.findById(99L);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals("Product not found.", e.getMessage());
        }
    }

    @Test
    void alterarProduto() {
        ProductDto newProduct = buildProduct();
        Long savedId = productService.save(newProduct);

        ProductDto recovered = productService.findById(savedId);
        recovered.setDescription("Modified description");
        productService.save(recovered);
        ProductDto recoveredEdited = productService.findById(savedId);
        assertEquals("Modified description", recoveredEdited.getDescription());
    }

    @Test
    void lancarExceptionAoCriarProdutoNulo() {
        try {
            productService.save(null);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals("Error saving product.", e.getMessage());
        }
    }

    @Test
    void removerProduto() {
        ProductDto newProduct = buildProduct();
        Long savedId = productService.save(newProduct);

        ProductDto recoveredBeforeDelete = productService.findById(savedId);
        assertNotNull(recoveredBeforeDelete);

        productService.deleteById(savedId);

        ProductDto attemptRecover = null;
        try {
            attemptRecover = productService.findById(savedId);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals("Product not found.", e.getMessage());
        }
        assertNull(attemptRecover);
    }

    @Test
    void lancarExceptionAoRemoverProdutoInexistente() {
        try {
            productService.deleteById(99L);
        } catch (Exception e) {
            assertTrue(e instanceof GenericException);
            assertEquals("Product not found.", e.getMessage());
        }
    }
}