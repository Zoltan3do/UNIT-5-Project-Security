package manu_barone.EventsOrganizer.repositories;

import manu_barone.EventsOrganizer.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UtenteRepo extends JpaRepository<Utente, UUID> {

}
