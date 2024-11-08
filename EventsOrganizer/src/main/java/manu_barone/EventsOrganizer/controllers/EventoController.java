package manu_barone.EventsOrganizer.controllers;

import jakarta.validation.Valid;
import manu_barone.EventsOrganizer.entities.Evento;
import manu_barone.EventsOrganizer.entities.Utente;
import manu_barone.EventsOrganizer.entities.enums.Ruolo;
import manu_barone.EventsOrganizer.exceptions.BadRequestException;
import manu_barone.EventsOrganizer.exceptions.NotFoundException;
import manu_barone.EventsOrganizer.payloads.EventoDTO;
import manu_barone.EventsOrganizer.payloads.UtenteDTO;
import manu_barone.EventsOrganizer.services.EventoService;
import manu_barone.EventsOrganizer.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService es;

    @Autowired
    private UtenteService us;

    @GetMapping
    public Page<Evento> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "username") String sortBy) {
        return this.es.findAll(page, size, sortBy);
    }

    @PostMapping("/creaEvento")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento save(@RequestBody @Validated EventoDTO body, BindingResult validationResult) throws NotFoundException {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        if (us.findById(body.organizzatoreId()) == null || us.findById(body.organizzatoreId()).getRuolo().equals(Ruolo.BASE)) {
            throw new NotFoundException("Organizzatore non valido");
        }
        return this.es.save(body);
    }

    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento findByIdAndUpdate(@PathVariable UUID eventId, @RequestBody EventoDTO body, BindingResult br) throws NotFoundException {
        if (br.hasErrors()) {
            br.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Errori nel payload");
        }
        return this.es.findByIdAndUpdate(eventId, body);
    }

    @PostMapping("{eventoid}/addPartecipante/{utenteId}")
    public Evento aggiungiPartecipante(@PathVariable UUID eventoId, @RequestBody EventoDTO event, @PathVariable UUID utenteId, @RequestBody UtenteDTO user) throws NotFoundException {
        if(es.isEventoPieno(eventoId)) throw new BadRequestException("L'evento ha raggiunto il numero massimo di posti");
        Evento evento = es.findById(eventoId);
        Utente utente = us.findById(utenteId);
        if (evento == null || utente == null) {
            throw new NotFoundException("Evento o utente non trovato");
        }
        es.savePartecipazione(evento, utente);
        us.savePartecipazione(utente, evento);
        return evento;
    }


}
