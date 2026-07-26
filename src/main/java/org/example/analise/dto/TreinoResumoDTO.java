package org.example.analise.dto;

import lombok.Builder;

@Builder
public record TreinoResumoDTO(
        Double distanciaKm,
        Integer duracaoMinutos,
        Double pace,
        Integer frequenciaMedia
) {}