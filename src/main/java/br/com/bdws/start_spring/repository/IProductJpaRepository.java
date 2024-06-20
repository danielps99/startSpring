package br.com.bdws.start_spring.repository;

import br.com.bdws.start_spring.entity.Product;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface IProductJpaRepository extends JpaRepository<Product, Long> {
}