package micromobility.payment;
/**
 * Excepción que indica que no hay suficiente saldo en el monedero.
 */
class NotEnoughWalletException extends Exception {
    public NotEnoughWalletException(String message) {
        super(message);
    }
}