package manu_barone.EventsOrganizer.controllers;

import manu_barone.EventsOrganizer.entities.Utente;
import manu_barone.EventsOrganizer.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService us;

    @GetMapping
    public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "username") String sortBy) {
        return this.us.findAll(page, size, sortBy);
    }

    @GetMapping("{id}/perEvento")
    public Page<Utente> findByEvento(@PathVariable UUID id,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "username") String sortBy) {
        return this.us.findByEvento(page, size, sortBy, id);
    }

}
