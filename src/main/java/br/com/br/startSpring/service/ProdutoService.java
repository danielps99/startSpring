package br.com.br.startSpring.service;

import br.com.br.startSpring.entity.Produto;
import br.com.br.startSpring.exception.GenericException;
import br.com.br.startSpring.repository.IProdutoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoJpaRepository jpaRepository;

    public Produto findById(Long id) {
        return jpaRepository.findById(id).orElseThrow(
                () -> new GenericException("Produto não encontrado.")
        );
    }

    public List<Produto> findAll() {
        return jpaRepository.findAll();
    }

    public Produto salvar(Produto produto) {
        try {
            return jpaRepository.save(produto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException("Erro ao salvar produto.");
        }
    }

    public void removerById(Long id) {
        try {
            jpaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new GenericException("Produto não encontrado.");
        }
    }
}