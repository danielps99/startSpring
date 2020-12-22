package br.com.br.startSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto {
    private Long id;
    private String sku;
    private String descricao;
    private String unidadeMedida;
    private BigDecimal unidade;
}