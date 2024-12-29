package services.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.ServiceID;
import payment.Wallet;
import payment.WalletPayment;
import services.exceptions.NotCorrectFormatException;
import services.exceptions.NotEnoughWalletException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para la clase WalletPayment.
 */
public class WalletPaymentTests {

    private Wallet wallet;
    private ServiceID serviceID;
    private WalletPayment walletPayment;

    @BeforeEach
    public void setUp() throws NotCorrectFormatException {
        wallet = new Wallet(new BigDecimal("100.00"));
        serviceID = new ServiceID("ABCDE12345");
        walletPayment = new WalletPayment(serviceID, "user@example.com", wallet);
    }

    /**
     * Test para verificar el procesamiento de un pago válido con WalletPayment.
     */
    @Test
    public void testWalletPaymentProcess_Valid() throws NotEnoughWalletException {
        walletPayment.processPayment(new BigDecimal("20.00"));
        assertEquals(new BigDecimal("80.00"), wallet.getBalance());
    }

    /**
     * Test para verificar que un pago con importe mayor al saldo lanza una excepción.
     */
    @Test
    public void testWalletPaymentProcess_InsufficientFunds() {
        assertThrows(NotEnoughWalletException.class, () -> walletPayment.processPayment(new BigDecimal("200.00")));
    }

    /**
     * Test para verificar que un WalletPayment no puede procesarse con un importe nulo o negativo.
     */
    @Test
    public void testWalletPaymentProcess_InvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> walletPayment.processPayment(null));
        assertThrows(IllegalArgumentException.class, () -> walletPayment.processPayment(new BigDecimal("-50.00")));
    }

    /**
     * Test para verificar la creación de un WalletPayment con datos válidos.
     */
    @Test
    public void testWalletPaymentCreation_Valid() {
        assertNotNull(walletPayment);
        assertEquals(serviceID, walletPayment.getServiceID());
        assertEquals("user@example.com", walletPayment.getUserAccount());
        assertEquals(wallet, walletPayment.getWallet());
    }

    /**
     * Test para verificar que no se puede crear un WalletPayment con datos inválidos.
     */
    @Test
    public void testWalletPaymentCreation_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(null, "user@example.com", wallet));
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(serviceID, null, wallet));
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(serviceID, "", wallet));
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(serviceID, "user@example.com", null));
    }
}
