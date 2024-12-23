package services.exceptions;

/**
 * Excepción lanzada cuando no se encuentra información del emparejamiento en el servidor.
 */
public class PairingNotFoundException extends Exception {
    public PairingNotFoundException(String message) {
        super(message);
    }

    public PairingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
