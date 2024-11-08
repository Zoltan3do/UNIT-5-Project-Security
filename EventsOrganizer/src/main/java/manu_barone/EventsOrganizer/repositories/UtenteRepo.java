package manu_barone.EventsOrganizer.repositories;

import manu_barone.EventsOrganizer.entities.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepo extends JpaRepository<Utente, UUID> {
    Optional<Utente> findByUsername(String username);
    Page<Utente> findByEventiPartecipatiId(Pageable pageable, UUID eventoId);

}
