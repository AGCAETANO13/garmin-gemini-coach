package org.example.service;
import org.example.client.GeminiClient;
import org.example.model.Treino;
import org.example.repository.TreinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final GeminiClient geminiClient;

    // Injeção de dependências via construtor
    public TreinoService(TreinoRepository treinoRepository, GeminiClient geminiClient) {
        this.treinoRepository = treinoRepository;
        this.geminiClient = geminiClient;
    }

    /**
     * Processa um novo treino: envia os dados para o Gemini analisar,
     * recebe o feedback do treinador virtual e salva tudo no PostgreSQL.
     */
    public Treino analisarESalvarTreino(Double distanciaKm, String tempoTotal, String paceMedio, Integer fcMedia) {

        // 1. Monta o prompt personalizado com os dados da corrida para o Gemini
        String prompt = String.format(
                "Analise este treino de corrida com as seguintes métricas:\n" +
                        "- Distância: %.2f km\n" +
                        "- Tempo Total: %s\n" +
                        "- Pace Médio: %s\n" +
                        "- Frequência Cardíaca Média: %d bpm\n\n" +
                        "Atue como um treinador de corrida experiente, fornecendo um feedback motivador, " +
                        "uma análise técnica do pace e sugestões para os próximos treinos.",
                distanciaKm, tempoTotal, paceMedio, fcMedia
        );

        // 2. Chama a API do Gemini utilizando o client já integrado
        String analiseIa = geminiClient.enviarPromptParaGemini(prompt);

        // 3. Cria a entidade e preenche com os dados e a resposta da IA
        Treino treino = new Treino(distanciaKm, tempoTotal, paceMedio, fcMedia, analiseIa);

        // 4. Salva no banco de dados PostgreSQL e retorna o objeto persistido
        return treinoRepository.save(treino);
    }

    /**
     * Retorna todos os treinos já salvos no histórico.
     */
    public List<Treino> listarHistoricoTreinos() {
        return treinoRepository.findAll();
    }
}