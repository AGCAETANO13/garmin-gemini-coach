package org.example.controller;

import org.example.analise.dto.TreinoResumoDTO; // Ou o DTO que você estiver usando para receber do App
import org.example.model.Treino;
import org.example.service.TreinoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/treinos")
@CrossOrigin(origins = "*")
public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    // Endpoint de status
    @GetMapping("/status")
    public ResponseEntity<String> verificarStatusApp() {
        return ResponseEntity.ok("API da Assessoria de Corrida online e pronta para o App!");
    }

    @PostMapping("/analisar")
    public ResponseEntity<Treino> analisarE_SalvarTreino(@RequestBody TreinoResumoDTO dto) {
        // Converte os dados do DTO para os tipos esperados pelo Service
        Treino treinoSalvo = treinoService.analisarESalvarTreino(
                dto.distanciaKm(),
                dto.duracaoMinutos() != null ? dto.duracaoMinutos() + " min" : "0 min", // Converte Integer para String
                dto.pace() != null ? String.valueOf(dto.pace()) : "0.0",               // Converte Double para String
                dto.frequenciaMedia()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(treinoSalvo);
    }

    // Endpoint para listar o histórico
    @GetMapping
    public ResponseEntity<List<Treino>> listarHistorico() {
        return ResponseEntity.ok(treinoService.listarHistoricoTreinos());
    }
}