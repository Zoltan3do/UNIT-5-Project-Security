package manu_barone.EventsOrganizer.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record LoginDTO(
        @NotEmpty
        String username,

        @NotEmpty
        @NotNull
//        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\n",
//                message = "La password non segue i criteri comuni")
        String password
) {
}
