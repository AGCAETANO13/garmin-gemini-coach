package org.example.analise.dto;

import lombok.Builder;

@Builder
public record AnaliseTreinoDTO(
        String atleta,
        TreinoResumoDTO treinoAtual,
        PerformanceDTO performance,
        CargaTreinoDTO carga,
        FadigaDTO fadiga
) {}