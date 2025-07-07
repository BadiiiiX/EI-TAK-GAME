package fr.esiea.mali.core.model.match.exceptions;

public class TimeLimitExceededException extends RuntimeException {
    public TimeLimitExceededException(String message) {
        super(message);
    }
}
