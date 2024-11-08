package manu_barone.EventsOrganizer.exceptions;

import java.util.UUID;

public class NotFoundException extends Exception{

    public NotFoundException(UUID id){
        super("Il record con id " + id + " non è stato trovato");
    }

    public NotFoundException(String message){
        super(message);
    }

}
