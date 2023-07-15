package br.com.bdws.startSpring.repository;

import br.com.bdws.startSpring.entity.Product;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface IProductJpaRepository extends JpaRepository<Product, Long> {
}