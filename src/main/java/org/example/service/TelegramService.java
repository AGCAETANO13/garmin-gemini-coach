package org.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramService {

    // Substitua pelo seu Token gerado pelo BotFather
    private static final String BOT_TOKEN = "8982030789:AAHGZ_0LvmAmNuD-EUIpFHdzuOdYPvZczFs";

    // Chat ID da Lidiane que acabamos de capturar
    private static final String CHAT_ID = "8822594436";

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean enviarMensagem(String mensagemTexto) {
        String url = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";

        Map<String, String> payload = new HashMap<>();
        payload.put("chat_id", CHAT_ID);
        payload.put("text", mensagemTexto);
//      payload.put("parse_mode", "Markdown"); // Permite usar negrito/itálico se quiser formatar o texto

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, payload, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}