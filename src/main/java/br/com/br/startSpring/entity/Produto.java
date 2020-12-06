package br.com.br.startSpring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
public class Produto {
    @Id
    private Long id;
    private String sku;
    private String descricao;
    @Column(name = "UNIDADEMEDIDA")
    private String unidadeMedida;
    @Column(name = "unidade", scale = 6, precision = 2)
    private BigDecimal unidade;

    public Produto() {
    }

    public Produto(Long id, String sku, String descricao, String unidadeMedida, BigDecimal unidade) {
        this.id = id;
        this.sku = sku;
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida;
        this.unidade = unidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public BigDecimal getUnidade() {
        BigDecimal bigDecimal = unidade.setScale(2, RoundingMode.HALF_UP);
        return unidade;
    }

    public void setUnidade(BigDecimal unidade) {
        this.unidade = unidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id.equals(produto.id) && sku.equals(produto.sku) && descricao.equals(produto.descricao) && unidadeMedida.equals(produto.unidadeMedida) && unidade.equals(produto.unidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sku, descricao, unidadeMedida, unidade);
    }
}