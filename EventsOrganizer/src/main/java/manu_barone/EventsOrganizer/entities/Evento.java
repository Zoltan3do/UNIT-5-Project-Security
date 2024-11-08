package manu_barone.EventsOrganizer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "eventi")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Evento {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String titolo;
    private String descrizione;
    private LocalDate data;
    private String luogo;
    private int postiDisponibili;

    @ManyToMany(mappedBy = "eventiPartecipati")
    private List<Utente> partecipanti;

    @ManyToOne
    @JoinColumn(name="organizzatore_id")
    private Utente organizzatore;

    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int postiDisponibili, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.postiDisponibili = postiDisponibili;
        this.organizzatore = organizzatore;
    }
}
