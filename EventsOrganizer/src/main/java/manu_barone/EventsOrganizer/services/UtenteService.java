package manu_barone.EventsOrganizer.services;

import jakarta.validation.constraints.NotEmpty;
import manu_barone.EventsOrganizer.entities.Evento;
import manu_barone.EventsOrganizer.entities.Utente;
import manu_barone.EventsOrganizer.exceptions.NotFoundException;
import manu_barone.EventsOrganizer.payloads.EventoDTO;
import manu_barone.EventsOrganizer.payloads.UtenteDTO;
import manu_barone.EventsOrganizer.repositories.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepo ur;

    @Autowired
    private PasswordEncoder bCrypt;

    public Utente save(UtenteDTO body){
        Utente u = new Utente(body.ruolo(),body.username(),bCrypt.encode(body.password()),body.nomeCompleto());
        return this.ur.save(u);
    }

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ur.findAll(pageable);
    }

    public Page<Utente> findByEvento(int page, int size, String sortBy, UUID evento) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ur.findByEventiPartecipatiId(pageable, evento);
    }

    public Utente findById(UUID utenteId) throws NotFoundException {
        return this.ur.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException {
        this.ur.delete(this.findById(id));
    }

    public Utente findByIdAndUpdate(UUID id, UtenteDTO body) throws NotFoundException {
        Utente e = this.findById(id);
        e.setRuolo(body.ruolo());
        e.setUsername(body.username());
        e.setPassword(body.password());
        e.setNomeCompleto(body.nomeCompleto());
        return this.ur.save(e);
    }

    public Utente savePartecipazione(Utente utente,Evento evento){
        utente.getEventiPartecipati().add(evento);
        return  this.ur.save(utente);
    }

    public Utente findByUsername(@NotEmpty String username) throws NotFoundException {
        return this.ur.findByUsername(username).orElseThrow(()-> new NotFoundException("Ciao"));
    }
}
