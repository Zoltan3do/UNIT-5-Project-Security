package manu_barone.EventsOrganizer.payloads;

import java.time.LocalDateTime;

public record ErrorsRespDTO(
        String message, LocalDateTime timestamp
) {
}
