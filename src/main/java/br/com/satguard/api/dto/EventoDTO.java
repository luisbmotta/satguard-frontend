package br.com.satguard.api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventoDTO {

    private Long id;
    private String tipo;
    private String descricao;
    private Double latitude;
    private Double longitude;
    private Double intensidade;
    private String pais;
    private LocalDateTime dataEvento;
    private Boolean favorito;
}