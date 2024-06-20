package br.com.bdws.startSpring.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for product entity.")
public class ProductDto {
    @Schema(description = "Id use in database table. Remove this field when you are creating product.")
    private Long id;
    @Schema(description = "Stock Keeping Unit.")
    private String sku;
    @Schema(description = "Product description.")
    private String description;
    @Schema(description = "Measured unit of the product.")
    private String measurementUnit;
    @Schema(description = "Unit price of the product.")
    private BigDecimal unitPrice;
}