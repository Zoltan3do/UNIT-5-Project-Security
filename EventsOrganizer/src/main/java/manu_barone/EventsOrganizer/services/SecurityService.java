package manu_barone.EventsOrganizer.services;

import manu_barone.EventsOrganizer.entities.Utente;
import manu_barone.EventsOrganizer.exceptions.NotFoundException;
import manu_barone.EventsOrganizer.exceptions.UnothorizedException;
import manu_barone.EventsOrganizer.payloads.LoginDTO;
import manu_barone.EventsOrganizer.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private UtenteService us;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body) throws NotFoundException {
        Utente found = this.us.findByUsername(body.username());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwt.createToken(found);
        } else {
            throw new UnothorizedException("Credenziali errate!");
        }
    }
}
