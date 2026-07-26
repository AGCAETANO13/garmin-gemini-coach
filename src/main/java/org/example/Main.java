package org.example;

import org.example.model.AtividadeGarmin;
import org.example.service.AssessoriaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Bean
    public CommandLineRunner executarSimulacao(AssessoriaService assessoriaService) {
        return args -> {
            AtividadeGarmin treinoFalso = new AtividadeGarmin(
                    "Corrida ao Ar Livre",
                    "Garmin Forerunner",
                    5.2,
                    28.5,
                    5,
                    31,
                    150,
                    320
            );

            assessoriaService.processarTreino("Lidiane", treinoFalso);

            System.out.println(">> Treino simulado injetado com sucesso na memória do servidor!");
        };
    }
}