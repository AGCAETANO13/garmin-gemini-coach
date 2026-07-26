package org.example.analise.dto;

import lombok.Builder;

@Builder
public record PerformanceDTO(
        String tendencia,
        Double evolucaoPaceSegundos,
        String descricao
) {}