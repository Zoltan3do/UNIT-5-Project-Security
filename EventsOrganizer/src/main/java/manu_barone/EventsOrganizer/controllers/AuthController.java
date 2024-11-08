package manu_barone.EventsOrganizer.controllers;

import manu_barone.EventsOrganizer.entities.Utente;
import manu_barone.EventsOrganizer.exceptions.BadRequestException;
import manu_barone.EventsOrganizer.exceptions.NotFoundException;
import manu_barone.EventsOrganizer.payloads.LoginDTO;
import manu_barone.EventsOrganizer.payloads.LoginReponseDTO;
import manu_barone.EventsOrganizer.payloads.UtenteDTO;
import manu_barone.EventsOrganizer.services.SecurityService;
import manu_barone.EventsOrganizer.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private SecurityService ss;

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public LoginReponseDTO LoginResponseDTO(@RequestBody @Validated LoginDTO body, BindingResult validationResult) throws NotFoundException {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return new LoginReponseDTO(this.ss.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente save(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.utenteService.save(body);
    }

}
