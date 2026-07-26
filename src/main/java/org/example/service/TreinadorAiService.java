package org.example.service;

import org.example.client.GeminiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreinadorAiService {

    @Autowired
    private GeminiClient geminiClient;

    public String analisarTreinoDaLidiane(double distanciaKm, double tempoMinutos, double paceMedio, int frequenciaCardiaca) {
        String prompt = String.format(
                "Você é um treinador de corrida profissional. Analise o treino a seguir: " +
                        "Distância: %.2f km, Tempo: %.2f min, Pace médio: %.2f min/km, Frequência Cardíaca Média: %d bpm. " +
                        "Dê um feedback construtivo e direto sobre este desempenho.",
                distanciaKm, tempoMinutos, paceMedio, frequenciaCardiaca
        );

        return geminiClient.enviarPromptParaGemini(prompt);
    }
}