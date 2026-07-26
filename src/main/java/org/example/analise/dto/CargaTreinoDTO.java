package org.example.analise.dto;

import lombok.Builder;

@Builder
public record CargaTreinoDTO(
        Double cargaAtual,
        String classificacao
) {}