package br.com.br.startSpring.service;

import br.com.br.startSpring.entity.Produto;
import br.com.br.startSpring.repository.IProdutoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoJpaRepository jpaRepository;

    public Produto findById(Long id) {
        return jpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Produto não encontrado.")
        );
    }

    public Produto salvar(Produto produto) {
        try {
            return jpaRepository.save(produto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar produto.");
        }
    }
}