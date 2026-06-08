package br.com.satguard.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // QUEIMADA ou TERREMOTO

    private String descricao;

    private Double latitude;

    private Double longitude;

    private Double intensidade;

    private String pais;

    private LocalDateTime dataEvento;

    private Boolean favorito = false;
}