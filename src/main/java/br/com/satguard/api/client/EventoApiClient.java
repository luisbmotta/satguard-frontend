package br.com.satguard.api.client;

import br.com.satguard.api.model.Evento;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventoApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Evento> buscarAsteroides() {
        List<Evento> eventos = new ArrayList<>();
        try {
            String url = "https://api.nasa.gov/neo/rest/v1/feed/today?detailed=false&api_key=DEMO_KEY";
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);
            JsonNode neos = root.path("near_earth_objects");

            neos.fields().forEachRemaining(entry -> {
                for (JsonNode neo : entry.getValue()) {
                    Evento e = new Evento();
                    e.setTipo("ASTEROIDE");
                    boolean perigoso = neo.path("is_potentially_hazardous_asteroid").asBoolean();
                    double diametro = neo.path("estimated_diameter")
                            .path("kilometers")
                            .path("estimated_diameter_max").asDouble();
                    double velocidade = neo.path("close_approach_data").get(0)
                            .path("relative_velocity")
                            .path("kilometers_per_hour").asDouble();
                    e.setDescricao(neo.path("name").asText() +
                            (perigoso ? " ⚠️ POTENCIALMENTE PERIGOSO" : " - Sem risco imediato"));
                    e.setIntensidade(diametro);
                    e.setLatitude(-15.0);
                    e.setLongitude(-47.0);
                    e.setPais("BR");
                    e.setDataEvento(LocalDateTime.now());
                    e.setFavorito(false);
                    eventos.add(e);
                }
            });
        } catch (Exception ex) {
            System.out.println("Erro ao buscar asteroides: " + ex.getMessage());
        }
        return eventos;
    }

    public List<Evento> buscarTerremotos() {
        List<Evento> eventos = new ArrayList<>();
        try {
            // API pública do USGS - terremotos na região do Brasil
            String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson" +
                    "&minlatitude=-33&maxlatitude=5" +
                    "&minlongitude=-73&maxlongitude=-34" +
                    "&minmagnitude=2.0&limit=20&orderby=time";

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);
            JsonNode features = root.path("features");

            for (JsonNode feature : features) {
                JsonNode props = feature.path("properties");
                JsonNode coords = feature.path("geometry").path("coordinates");

                Evento e = new Evento();
                e.setTipo("TERREMOTO");
                e.setDescricao("Magnitude " + props.path("mag").asDouble() +
                        " - " + props.path("place").asText());
                e.setLongitude(coords.get(0).asDouble());
                e.setLatitude(coords.get(1).asDouble());
                e.setIntensidade(props.path("mag").asDouble());
                e.setPais("BR");

                long time = props.path("time").asLong();
                e.setDataEvento(LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(time),
                        ZoneId.systemDefault()));
                e.setFavorito(false);
                eventos.add(e);
            }
        } catch (Exception ex) {
            System.out.println("Erro ao buscar terremotos: " + ex.getMessage());
        }
        return eventos;
    }
}