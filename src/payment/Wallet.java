package payment;

import services.exceptions.*;
import java.math.BigDecimal;
/**
 * Clase que representa el monedero asociado a un usuario.
 */
public class Wallet {
    private BigDecimal balance;

    public Wallet(BigDecimal initialBalance) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo.");
        }
        this.balance = initialBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deduct(BigDecimal amount) throws NotEnoughWalletException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe a deducir debe ser positivo.");
        }
        if (balance.compareTo(amount) < 0) {
            throw new NotEnoughWalletException("Saldo insuficiente en el monedero.");
        }
        balance = balance.subtract(amount);
    }

    @Override
    public String toString() {
        return "Wallet{" + "balance=" + balance + '}';
    }
}