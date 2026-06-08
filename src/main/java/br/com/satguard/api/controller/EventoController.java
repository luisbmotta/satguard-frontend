package br.com.satguard.api.controller;

import br.com.satguard.api.model.Evento;
import br.com.satguard.api.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventoController {

    private final EventoService service;

    @GetMapping
    public ResponseEntity<List<Evento>> listar(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String pais) {
        return ResponseEntity.ok(service.filtrar(tipo, pais));
    }

    @GetMapping("/favoritos")
    public ResponseEntity<List<Evento>> favoritos() {
        return ResponseEntity.ok(service.listarFavoritos());
    }

    @PatchMapping("/{id}/favorito")
    public ResponseEntity<Evento> toggleFavorito(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggleFavorito(id));
    }

    @PostMapping("/sincronizar")
    public ResponseEntity<String> sincronizar() {
        service.sincronizar();
        return ResponseEntity.ok("Sincronização concluída!");
    }
}