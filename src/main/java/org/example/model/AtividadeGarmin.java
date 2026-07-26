package org.example.model;

public record AtividadeGarmin(
        String tipoAtividade,
        String dispositivo,
        double distanciaKm,
        double tempoMinutos,
        int paceMinutos,
        int paceSegundos,
        int fcMedia,
        int calorias
) {}