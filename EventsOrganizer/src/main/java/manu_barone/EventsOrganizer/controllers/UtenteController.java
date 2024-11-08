package manu_barone.EventsOrganizer.controllers;

import manu_barone.EventsOrganizer.entities.Utente;
import manu_barone.EventsOrganizer.exceptions.NotFoundException;
import manu_barone.EventsOrganizer.payloads.UtenteDTO;
import manu_barone.EventsOrganizer.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService us;


    // ************************ ME ENDPOINTS **************************

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente current) {
        return current;
    }

    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente current, @RequestBody @Validated UtenteDTO body) throws NotFoundException {
        return this.us.findByIdAndUpdate(current.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente current) throws NotFoundException {
        this.us.findByIdAndDelete(current.getId());
    }

    
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
