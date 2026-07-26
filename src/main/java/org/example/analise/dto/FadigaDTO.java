package org.example.analise.dto;

import lombok.Builder;

@Builder
public record FadigaDTO(
        String risco,
        String motivo
) {}