package org.example.analise.calculator;

import org.example.analise.dto.CargaTreinoDTO;
import org.example.model.AtividadeGarmin;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CargaTreinoCalculator {
    public CargaTreinoDTO calcular(List<AtividadeGarmin> treinos) {
        return CargaTreinoDTO.builder()
                .cargaAtual(30.0)
                .classificacao("MODERADA")
                .build();
    }
}