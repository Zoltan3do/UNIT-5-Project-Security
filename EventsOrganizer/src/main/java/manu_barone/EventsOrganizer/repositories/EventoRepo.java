package manu_barone.EventsOrganizer.repositories;

import manu_barone.EventsOrganizer.entities.Evento;
import manu_barone.EventsOrganizer.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventoRepo extends JpaRepository<Evento, UUID> {
    @Query("SELECT COUNT(u) FROM Utente u JOIN u.eventiPartecipati e WHERE e.id = :eventoId")
    long countPartecipantiByEventoId(UUID eventoId);
}
