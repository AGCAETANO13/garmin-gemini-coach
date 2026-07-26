package org.example.analise.calculator;

import org.example.analise.dto.FadigaDTO;
import org.example.model.AtividadeGarmin;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FadigaCalculator {
    public FadigaDTO analisar(List<AtividadeGarmin> treinos) {
        return FadigaDTO.builder()
                .risco("BAIXO")
                .motivo("Boa recuperação entre os treinos")
                .build();
    }
}