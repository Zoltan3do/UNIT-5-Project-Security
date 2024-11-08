package manu_barone.EventsOrganizer.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg) {
        super(msg);
    }
}
