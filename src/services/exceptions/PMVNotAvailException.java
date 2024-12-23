package services.exceptions;

/**
 * Excepción lanzada cuando un vehículo no está disponible.
 */
public class PMVNotAvailException extends Exception {
    public PMVNotAvailException(String message) {
        super(message);
    }

    public PMVNotAvailException(String message, Throwable cause) {
        super(message, cause);
    }
}