/**
 * Excepción lanzada cuando los argumentos proporcionados no son válidos.
 */
public class NotValidArguments extends Exception {
    /**
     * Constructor con mensaje de error.
     *
     * @param message mensaje que describe el error.
     */
    public NotValidArguments(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje de error y causa.
     *
     * @param message mensaje que describe el error.
     * @param cause   la causa de la excepción.
     */
    public NotValidArguments(String message, Throwable cause) {
        super(message, cause);
    }
}