package org.example.analise.service;

import lombok.RequiredArgsConstructor;
import org.example.analise.calculator.CargaTreinoCalculator;
import org.example.analise.calculator.FadigaCalculator;
import org.example.analise.calculator.PerformanceCalculator;
import org.example.analise.dto.*;
import org.example.model.AtividadeGarmin;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotorAnaliseService {

    private final PerformanceCalculator performanceCalculator;
    private final CargaTreinoCalculator cargaCalculator;
    private final FadigaCalculator fadigaCalculator;

    public AnaliseTreinoDTO montarAnalise(String nomeAtleta, List<AtividadeGarmin> historico) {
        AtividadeGarmin ultimoTreino = historico.get(historico.size() - 1);

        // Calcula o pace decimal e arredonda para apenas 2 casas decimais (Ex: 5.38 em vez de 5.516666...)
        double paceDecimal = ultimoTreino.paceMinutos() + (ultimoTreino.paceSegundos() / 60.0);
        double paceArredondado = Math.round(paceDecimal * 100.0) / 100.0;

        return AnaliseTreinoDTO.builder()
                .atleta(nomeAtleta)
                .treinoAtual(TreinoResumoDTO.builder()
                        .distanciaKm(ultimoTreino.distanciaKm())
                        .duracaoMinutos((int) ultimoTreino.tempoMinutos())
                        .pace(paceArredondado) // Agora vai limpo e sem dízimas!
                        .frequenciaMedia(ultimoTreino.fcMedia())
                        .build())
                .performance(performanceCalculator.analisar(historico))
                .carga(cargaCalculator.calcular(historico))
                .fadiga(fadigaCalculator.analisar(historico))
                .build();
    }
}