package exceptions;

public class AverageFlightException extends RuntimeException {
    public AverageFlightException(String message) {
        super(message);
    }

    public AverageFlightException(String message, Throwable cause) {
        super(message, cause);
    }
}
