package br.com.satguard.api.service;

import br.com.satguard.api.client.EventoApiClient;
import br.com.satguard.api.model.Evento;
import br.com.satguard.api.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repository;
    private final EventoApiClient apiClient;

    public List<Evento> listarTodos() {
        if (repository.count() == 0) {
            sincronizar();
        }
        return repository.findAll();
    }

    public List<Evento> filtrar(String tipo, String pais) {
        if (tipo != null && pais != null) {
            return repository.findByTipoAndPais(tipo, pais);
        } else if (tipo != null) {
            return repository.findByTipo(tipo);
        } else if (pais != null) {
            return repository.findByPais(pais);
        }
        return repository.findAll();
    }

    public List<Evento> listarFavoritos() {
        return repository.findByFavoritoTrue();
    }

    public Evento toggleFavorito(Long id) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        evento.setFavorito(!evento.getFavorito());
        return repository.save(evento);
    }

    public void sincronizar() {
        repository.deleteAll();
        repository.saveAll(apiClient.buscarAsteroides());
        repository.saveAll(apiClient.buscarTerremotos());
    }
}