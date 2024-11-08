package manu_barone.EventsOrganizer.repositories;

import manu_barone.EventsOrganizer.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventoRepo extends JpaRepository<Evento, UUID> {
}
