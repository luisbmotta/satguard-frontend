package br.com.satguard.api.repository;

import br.com.satguard.api.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByTipo(String tipo);

    List<Evento> findByPais(String pais);

    List<Evento> findByTipoAndPais(String tipo, String pais);

    List<Evento> findByFavoritoTrue();
}