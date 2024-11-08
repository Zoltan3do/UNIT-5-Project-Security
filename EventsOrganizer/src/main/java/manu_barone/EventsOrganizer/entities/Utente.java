package manu_barone.EventsOrganizer.entities;

import jakarta.persistence.*;
import lombok.*;
import manu_barone.EventsOrganizer.entities.enums.Ruolo;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="utenti")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Utente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    private String username;
    private String password;
    private String nomeCompleto;

    @ManyToMany
    @JoinTable(
            name = "utenti_eventi",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name="evento_id")
    )
    private List<Evento> eventiPartecipati;

    @OneToMany(mappedBy = "organizzatore")
    private List<Evento> eventiOrganizzati;

    public Utente(Ruolo ruolo, String username, String password, String nomeCompleto, List<Evento> eventiPartecipati) {
        this.ruolo = ruolo;
        this.username = username;
        this.password = password;
        this.nomeCompleto = nomeCompleto;
        this.eventiPartecipati = eventiPartecipati;
    }

}
