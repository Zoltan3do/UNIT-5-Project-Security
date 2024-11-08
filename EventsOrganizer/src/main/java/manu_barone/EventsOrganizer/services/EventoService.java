package manu_barone.EventsOrganizer.services;

import manu_barone.EventsOrganizer.entities.Evento;
import manu_barone.EventsOrganizer.entities.Utente;
import manu_barone.EventsOrganizer.exceptions.NotFoundException;
import manu_barone.EventsOrganizer.payloads.EventoDTO;
import manu_barone.EventsOrganizer.repositories.EventoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventoService {

    @Autowired
    private EventoRepo er;

    @Autowired
    private UtenteService us;

    public Evento save(EventoDTO body) throws NotFoundException {
        Evento e = new Evento(body.titolo(), body.descrizione(), body.data(), body.luogo(), body.postiDisponibili(), us.findById(body.organizzatoreId()));
        return this.er.save(e);
    }

    public Page<Evento> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.er.findAll(pageable);
    }

    public Evento findById(UUID eventoId) throws NotFoundException {
        return this.er.findById(eventoId).orElseThrow(() -> new NotFoundException(eventoId));
    }

    public Evento findByIdAndUpdate(UUID id, EventoDTO body) throws NotFoundException {
        Evento e = this.findById(id);
        e.setTitolo(body.titolo());
        e.setData(body.data());
        e.setLuogo(body.luogo());
        e.setOrganizzatore(us.findById(body.organizzatoreId()));
        e.setDescrizione(body.descrizione());
        return this.er.save(e);
    }

    public void deleteEvent(UUID eventoId) throws NotFoundException {
        Evento e = this.findById(eventoId);
        this.er.delete(e);
    }

    public void savePartecipazione(Evento evento, Utente utente){
        evento.getPartecipanti().add(utente);
        this.er.save(evento);
    }

    public boolean isEventoPieno(UUID eventoId) throws NotFoundException{
        Evento evento = er.findById(eventoId).orElseThrow(()->new NotFoundException("Evento non trovato"));
        return er.countPartecipantiByEventoId(eventoId) >= evento.getPostiDisponibili();
    }

}
