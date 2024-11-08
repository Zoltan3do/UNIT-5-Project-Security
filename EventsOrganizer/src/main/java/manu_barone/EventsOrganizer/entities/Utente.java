package manu_barone.EventsOrganizer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import manu_barone.EventsOrganizer.entities.enums.Ruolo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="utenti")
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"password","role","enabled","accountNonLocked","credentialsNonExpired","accountNonExpired","authorities"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @Column(unique = true)
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

    public Utente(Ruolo ruolo, String username, String password, String nomeCompleto) {
        this.ruolo = ruolo;
        this.username = username;
        this.password = password;
        this.nomeCompleto = nomeCompleto;
}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }
}
