package manu_barone.EventsOrganizer.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import manu_barone.EventsOrganizer.entities.enums.Ruolo;

public record UtenteDTO (
        @NotNull
        Ruolo ruolo,
        @NotEmpty
        @Size(min=6, message = "username tropppo breve")
        String username,
        @NotEmpty
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\n", message
        ="La password non segue i criteri comuni")
        String password,
        @NotEmpty
        @Size(min=6, message = "Nome completo troppo breve")
        String nomeCompleto
){
}
