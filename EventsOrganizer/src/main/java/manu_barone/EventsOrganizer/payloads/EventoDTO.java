package manu_barone.EventsOrganizer.payloads;

import jakarta.validation.constraints.*;
import manu_barone.EventsOrganizer.entities.Utente;

import java.time.LocalDate;
import java.util.UUID;

public record EventoDTO(
        @NotEmpty
        String titolo,
        @NotEmpty
        @Size(min=10, message = "descrizione troppo breve")
        String descrizione,
        @Future(message = "Evento gia passato")
        @NotNull
        LocalDate data,
        @NotEmpty
        String luogo,
        @NotNull
        @Min(value = 10, message = "troppi pochi posti per creare questo evento")
        int postiDisponibili,
        @NotNull
        UUID organizzatoreId
) {
}
