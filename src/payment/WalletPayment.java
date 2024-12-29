package micromobility.payment;

import java.math.BigDecimal;
/**
 * Representa un pago realizado mediante el monedero de la app.
 */
class WalletPayment extends Payment {
    private final Wallet wallet;

    public WalletPayment(ServiceID serviceID, String userAccount, Wallet wallet) {
        super(serviceID, userAccount);
        if (wallet == null) {
            throw new IllegalArgumentException("El monedero no puede ser nulo.");
        }
        this.wallet = wallet;
    }

    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public void processPayment(BigDecimal amount) throws NotEnoughWalletException {
        wallet.deduct(amount);
    }
}