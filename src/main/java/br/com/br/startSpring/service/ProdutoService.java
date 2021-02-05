package br.com.br.startSpring.service;

import br.com.br.startSpring.dto.ProdutoDto;
import br.com.br.startSpring.entity.Produto;
import br.com.br.startSpring.exception.GenericException;
import br.com.br.startSpring.repository.IProdutoJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoJpaRepository jpaRepository;
    @Autowired
    private ModelMapper mapper;

    public ProdutoDto findById(Long id) {
        Produto produto = jpaRepository.findById(id).orElseThrow(
                () -> new GenericException("Produto não encontrado.")
        );
        return mapper.map(produto, ProdutoDto.class);
    }

    public List<ProdutoDto> findAll() {
        return jpaRepository.findAll().stream()
                .map(book -> mapper.map(book, ProdutoDto.class)).collect(Collectors.toList());
    }

    public Long save(ProdutoDto dto) {
        try {
            Produto produto = mapper.map(dto, Produto.class);
            Produto salvo = jpaRepository.save(produto);
            return salvo.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException("Erro ao salvar produto.");
        }
    }

    public void deleteById(Long id) {
        try {
            jpaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new GenericException("Produto não encontrado.");
        }
    }
}