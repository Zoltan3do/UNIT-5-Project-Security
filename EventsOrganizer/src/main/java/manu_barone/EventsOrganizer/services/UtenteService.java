package manu_barone.EventsOrganizer.services;

import manu_barone.EventsOrganizer.entities.Utente;
import manu_barone.EventsOrganizer.exceptions.NotFoundException;
import manu_barone.EventsOrganizer.payloads.UtenteDTO;
import manu_barone.EventsOrganizer.repositories.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepo ur;

    public Utente save(UtenteDTO body){
        Utente u = new Utente(body.ruolo(),body.username(),body.password(),body.nomeCompleto());
        return this.ur.save(u);
    }

    public Utente findById(UUID utenteId) throws NotFoundException {
        return this.ur.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }
}
