package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.analise.dto.AnaliseTreinoDTO;
import org.example.analise.service.MotorAnaliseService;
import org.example.client.GeminiClient;
import org.example.model.AtividadeGarmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessoriaService {

    @Autowired
    private GeminiClient geminiClient; // Corrigido para minúsculo e sem erro de digitação

    @Autowired
    private TelegramService telegramService;

    @Autowired
    private MotorAnaliseService motorAnaliseService;

    @Autowired
    private ObjectMapper objectMapper;

    private AtividadeGarmin ultimaAtividadeProcessada;

    public AtividadeGarmin obterUltimoTreino(String atleta) {
        return this.ultimaAtividadeProcessada;
    }

    // Prompt com gabarito fixo (Few-Shot Prompting) para blindar o tom e a estrutura
    private final String PROMPT_SYSTEM_TREINADOR = """
            Você é um treinador de corrida profissional, experiente, empático e de alto nível técnico da assessoria. 
            Seu objetivo é analisar estritamente os dados do relógio Garmin fornecidos em JSON e redigir um feedback idêntico em tom, emoção e estrutura ao exemplo abaixo.
            IMPORTANTE: A atleta é do gênero feminino. Utilize sempre pronomes, adjetivos e flexões estritamente no feminino ao se referir a ela (ex: "a atleta", "prepará-la", "dedicada", "focada"). NUNCA use o masculino.

            EXEMPLO DO PADRÃO EXIGIDO DE RESPOSTA:
            -------------------------------------
            Olá, Lidiane! Aqui é o seu treinador. 

            Que treino fantástico! Ver esses números consolidados no seu Garmin Forerunner 55 me dá uma clareza enorme da sua evolução. Completar 5 km abaixo dos 30 minutos (com folga!) é um marco respeitável e mostra que você está em excelente forma e com o comprometimento em dia.

            Abaixo, fiz uma análise técnica e detalhada da sua sessão para entendermos o que esses números significam e como usá-los a seu favor.

            ---

            ### 1. Seus Pontos Fortes neste Treino 
            * **Consistência no Volume:** Você fechou os quilômetros de forma redonda. Essa distância é um excelente termômetro de condicionamento físico geral.
            * **Ritmo e Determinação:** Sustentar um ritmo forte do início ao fim demonstra excelente disciplina mental.
            * **Uso da Tecnologia:** O relógio Garmin é um excelente aliado. A precisão do GPS e da leitura cardíaca nos dá dados reais e confiáveis.

            ---

            ### 2. Análise Técnica: Pace vs. Frequência Cardíaca 
            Aqui está o "ouro" do seu treino. Vamos cruzar o seu Pace com a sua Frequência Cardíaca média:
            * **Eficiência Cardiovascular:** Manter esse pace com a frequência cardíaca registrada é sinal claro de excelente eficiência aeróbica nas zonas de transição.
            * **O que isso significa na prática?** Esforço perceptível, porém totalmente controlled, sem entrar em zona anaeróbica extrema desnecessária.

            ---

            ### 3. Orientações e Prescrição para o Próximo Treino 
            Como o seu treino foi de intensidade moderada/alta, o nosso próximo passo será focar na recuperação ativa e na base aeróbica.
            - **Objetivo:** Estimular circulação e recuperar a musculatura.
            - **Pace Alvo e FC Alvo:** Defina metas claras e leves de regeneração.
            -------------------------------------

            REGRAS RÍGIDAS:
            1. Substitua os dados do exemplo pelos dados reais contidos no JSON enviado pelo usuário.
            2. NUNCA invente intensidades perigosas (ex: regenerativo com 180 bpm é proibido).
            3. Use um português humano, caloroso, fluído e encorajador, exatamente como no exemplo.
            4. Respeite obrigatoriamente a concordância gramatical no gênero feminino para a atleta.
            """;

    public void processarTreino(String nomeAtleta, AtividadeGarmin atividade) {
        System.out.println("Processando treino para: " + nomeAtleta);

        this.ultimaAtividadeProcessada = atividade;

        List<AtividadeGarmin> historico = List.of(atividade);

        // 1. O motor Java calcula as métricas e monta o DTO
        AnaliseTreinoDTO analiseDTO = motorAnaliseService.montarAnalise(nomeAtleta, historico);

        try {
            // 2. Transforma o DTO em JSON limpo
            String jsonDados = objectMapper.writeValueAsString(analiseDTO);

            // 3. Monta o prompt enviando o gabarito do sistema + os dados estruturados
            String prompt = """
                    %s
                    
                    DADOS REAIS DO TREINO DE HOJE (Extraídos do Garmin e processados pelo motor Java):
                    %s
                    
                    Gere o parecer técnico completo seguindo rigorosamente o tom e a estrutura do exemplo acima.
                    """.formatted(PROMPT_SYSTEM_TREINADOR, jsonDados);

            // 4. Chama o Gemini
            String analiseIa = geminiClient.enviarPromptParaGemini(prompt);

            // 5. Envia para o Telegram
            boolean enviado = telegramService.enviarMensagem(analiseIa);

            if (enviado) {
                System.out.println(">> Mensagem enviada com sucesso para o Telegram!");
            } else {
                System.out.println(">> Erro ao enviar mensagem para o Telegram.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar análise do treino: " + e.getMessage(), e);
        }

    }

    public AtividadeGarmin obterUltimaAtividade() {
        return this.ultimaAtividadeProcessada;
    }
}