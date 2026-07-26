package org.example.analise.calculator;

import org.example.analise.dto.PerformanceDTO;
import org.example.model.AtividadeGarmin;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PerformanceCalculator {
    public PerformanceDTO analisar(List<AtividadeGarmin> treinos) {
        return PerformanceDTO.builder()
                .tendencia("MELHORA")
                .evolucaoPaceSegundos(-10.0)
                .descricao("Evolução consistente")
                .build();
    }
}