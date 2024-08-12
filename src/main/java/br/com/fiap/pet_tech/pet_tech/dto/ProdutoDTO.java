package br.com.fiap.pet_tech.pet_tech.dto;

import java.util.UUID;

public record ProdutoDTO(
        UUID id,
        String nome,
        String descrição,
        double preco,
        String urlDaImagem
) {
}
