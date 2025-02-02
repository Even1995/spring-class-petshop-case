package br.com.fiap.pet_tech.pet_tech.service;

import br.com.fiap.pet_tech.pet_tech.dto.ProdutoDTO;
import br.com.fiap.pet_tech.pet_tech.entities.Produto;
import br.com.fiap.pet_tech.pet_tech.controller.exception.ControllerNotFoundException;
import br.com.fiap.pet_tech.pet_tech.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repo;

    public Collection<ProdutoDTO> findAll() {
        var produto = repo.findAll();
        return produto
                .stream()
                .map(this::toProdutoDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO findById(UUID id) {
        var produto = repo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Produto não encontrado"));
        return toProdutoDTO(produto);
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Produto produto = toProduto(produtoDTO);
        produto = repo.save(produto);
        return toProdutoDTO(produto);
    }

    public ProdutoDTO update(UUID id, ProdutoDTO produtoDTO) {
        try {
            Produto buscaProduto = repo.getReferenceById(id);
            buscaProduto.setNome(produtoDTO.nome());
            buscaProduto.setDescrição(produtoDTO.descrição());
            buscaProduto.setPreço(produtoDTO.preco());
            buscaProduto.setUrlDaImagem(produtoDTO.urlDaImagem());
            return toProdutoDTO(buscaProduto);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Produto não encontrado");
        }
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }

    private ProdutoDTO toProdutoDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescrição(),
                produto.getPreço(),
                produto.getUrlDaImagem()

        );
    }

    private Produto toProduto(ProdutoDTO produtoDTO) {
        return new Produto(
                produtoDTO.id(),
                produtoDTO.nome(),
                produtoDTO.descrição(),
                produtoDTO.preco(),
                produtoDTO.urlDaImagem()
        );
    }
}

