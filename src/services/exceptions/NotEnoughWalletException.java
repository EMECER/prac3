package services.exceptions;
/**
 * Excepción que indica que no hay suficiente saldo en el monedero.
 */
public class NotEnoughWalletException extends Exception {
    public NotEnoughWalletException(String message) {
        super(message);
    }
}