package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_treino")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double distanciaKm;
    private String tempoTotal;
    private String paceMedio;
    private Integer frequenciaCardiacaMedia;

    @Column(columnDefinition = "TEXT")
    private String analisaGemini;

    private LocalDateTime dataHoraRegistro = LocalDateTime.now();

    public Treino() {
    }

    public Treino(Double distanciaKm, String tempoTotal, String paceMedio, Integer frequenciaCardiacaMedia, String analisaGemini) {
        this.distanciaKm = distanciaKm;
        this.tempoTotal = tempoTotal;
        this.paceMedio = paceMedio;
        this.frequenciaCardiacaMedia = frequenciaCardiacaMedia;
        this.analisaGemini = analisaGemini;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) { this.distanciaKm = distanciaKm; }

    public String getTempoTotal() { return tempoTotal; }
    public void setTempoTotal(String tempoTotal) { this.tempoTotal = tempoTotal; }

    public String getPaceMedio() { return paceMedio; }
    public void setPaceMedio(String paceMedio) { this.paceMedio = paceMedio; }

    public Integer getFrequenciaCardiacaMedia() { return frequenciaCardiacaMedia; }
    public void setFrequenciaCardiacaMedia(Integer frequenciaCardiacaMedia) { this.frequenciaCardiacaMedia = frequenciaCardiacaMedia; }

    public String getAnalisaGemini() { return analisaGemini; }
    public void setAnalisaGemini(String analisaGemini) { this.analisaGemini = analisaGemini; }

    public LocalDateTime getDataHoraRegistro() { return dataHoraRegistro; }
    public void setDataHoraRegistro(LocalDateTime dataHoraRegistro) { this.dataHoraRegistro = dataHoraRegistro; }
}