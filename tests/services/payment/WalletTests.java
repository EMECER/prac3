package services.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import payment.Wallet;
import services.exceptions.NotEnoughWalletException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para la clase Wallet.
 */
public class WalletTests {

    private Wallet wallet;

    @BeforeEach
    public void setUp() {
        wallet = new Wallet(new BigDecimal("100.00"));
    }

    /**
     * Test para verificar el saldo inicial del monedero.
     */
    @Test
    public void testWalletInitialBalance() {
        assertEquals(new BigDecimal("100.00"), wallet.getBalance());
    }

    /**
     * Test para verificar la deducción correcta de un importe del monedero.
     */
    @Test
    public void testWalletDeduct_ValidAmount() throws NotEnoughWalletException {
        wallet.deduct(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("50.00"), wallet.getBalance());
    }

    /**
     * Test para verificar que no se puede deducir un importe mayor al saldo.
     */
    @Test
    public void testWalletDeduct_InsufficientFunds() {
        assertThrows(NotEnoughWalletException.class, () -> wallet.deduct(new BigDecimal("200.00")));
    }

    /**
     * Test para verificar que no se puede deducir un importe nulo o negativo.
     */
    @Test
    public void testWalletDeduct_InvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(null));
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(new BigDecimal("-10.00")));
    }
}
