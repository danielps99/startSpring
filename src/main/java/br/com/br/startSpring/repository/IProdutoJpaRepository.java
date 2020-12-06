package br.com.br.startSpring.repository;

import br.com.br.startSpring.entity.Produto;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface IProdutoJpaRepository extends JpaRepository<Produto, Long> {
}