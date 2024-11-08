package manu_barone.EventsOrganizer.tools;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import manu_barone.EventsOrganizer.entities.Utente;
import manu_barone.EventsOrganizer.exceptions.NotFoundException;
import manu_barone.EventsOrganizer.exceptions.UnothorizedException;
import manu_barone.EventsOrganizer.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTChecker  extends OncePerRequestFilter {

    @Autowired
    private JWT jwt;

    @Autowired
    private UtenteService us;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Inserire token nell' Authorization Header nel formato corretto !");
            return;
        }

        String accessToken = authorizationHeader.split(" ")[1];

        try {
            jwt.verifyToken(accessToken);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non valido o scaduto");
            return;
        }

        String idFromToken = jwt.getIdFromToken(accessToken);
        UUID idUtente;
        try {
            idUtente = UUID.fromString(idFromToken);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ID del token non valido");
            return;
        }

        Utente utenteCorrente;
        try {
            utenteCorrente = this.us.findById(idUtente);
            if (utenteCorrente == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utente non trovato");
                return;
            }
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utente non trovato");
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(utenteCorrente, null, utenteCorrente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
