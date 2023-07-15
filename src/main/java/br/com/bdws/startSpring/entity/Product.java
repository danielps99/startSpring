package br.com.bdws.startSpring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditTable(value = "PRODUCT_AUD")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String description;
    @Column(name = "MEASUREMENTUNIT")
    private String measurementUnit;
    @Column(name = "UNITPRICE", scale = 6, precision = 2)
    private BigDecimal unitPrice;
}