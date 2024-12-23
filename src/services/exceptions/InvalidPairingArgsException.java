package services.exceptions;

/**
 * Excepción lanzada cuando los argumentos para el emparejamiento son inválidos.
 */
public class InvalidPairingArgsException extends Exception {
    public InvalidPairingArgsException(String message) {
        super(message);
    }

    public InvalidPairingArgsException(String message, Throwable cause) {
        super(message, cause);
    }
}
