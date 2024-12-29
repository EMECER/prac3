package micromobility.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para las clases relacionadas con el sistema de pagos.
 */
public class PaymentTests {

    private Wallet wallet;
    private ServiceID serviceID;
    private WalletPayment walletPayment;

    @BeforeEach
    public void setUp() {
        wallet = new Wallet(new BigDecimal("100.00"));
        serviceID = new ServiceID("ABCDE12345");
        walletPayment = new WalletPayment(serviceID, "user@example.com", wallet);
    }

    /**
     * Test para verificar la creación correcta de un ServiceID válido.
     */
    @Test
    public void testServiceIDCreation_Valid() {
        ServiceID validID = new ServiceID("VALID123");
        assertEquals("VALID123", validID.getId());
    }

    /**
     * Test para verificar que un ServiceID nulo o inválido lanza una excepción.
     */
    @Test
    public void testServiceIDCreation_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceID(null));
        assertThrows(IllegalArgumentException.class, () -> new ServiceID(""));
        assertThrows(IllegalArgumentException.class, () -> new ServiceID("123")); // Menor longitud
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
