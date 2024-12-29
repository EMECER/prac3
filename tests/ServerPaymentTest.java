package services;

import data.ServiceID;
import data.UserAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la interfaz ServerPaymentInterface.
 * Simula y verifica el comportamiento esperado de los métodos definidos en la interfaz.
 */
public class ServerPaymentTest implements ServerPaymentInterface {

    private boolean isServerConnected;

    @BeforeEach
    public void setUp() {
        isServerConnected = true; // Simula que el servidor está disponible por defecto
    }

    @Override
    public void registerPayment(ServiceID servID, UserAccount user, BigDecimal imp, char payMeth) throws ConnectException {
        if (!isServerConnected) {
            throw new ConnectException("No se pudo conectar con el servidor.");
        }
        if (servID == null || user == null || imp == null || payMeth == '\0') {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos o inválidos.");
        }
        if (imp.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor a cero.");
        }
        if (payMeth != 'C' && payMeth != 'D' && payMeth != 'P') {
            throw new IllegalArgumentException("Método de pago inválido. Use 'C', 'D' o 'P'.");
        }
        // Simula un registro exitoso en el servidor.
    }

    // Pruebas unitarias

    /**
     * Prueba exitosa para registrar un pago.
     */
    @Test
    public void testRegisterPayment_Success() {
        ServiceID serviceID = new ServiceID("1234");
        UserAccount user = new UserAccount("user@example.com");
        BigDecimal amount = new BigDecimal("25.50");
        char paymentMethod = 'C'; // 'C' para tarjeta de crédito

        assertDoesNotThrow(() -> registerPayment(serviceID, user, amount, paymentMethod));
    }

    /**
     * Prueba que verifica que no se puede registrar un pago con un importe negativo.
     */
    @Test
    public void testRegisterPayment_NegativeAmount() {
        ServiceID serviceID = new ServiceID("12345");
        UserAccount user = new UserAccount("user@example.com");
        BigDecimal negativeAmount = new BigDecimal("-10.00");
        char paymentMethod = 'D'; // 'D' para débito

        assertThrows(IllegalArgumentException.class, () -> registerPayment(serviceID, user, negativeAmount, paymentMethod));
    }

    /**
     * Prueba que verifica que no se puede registrar un pago con un método de pago inválido.
     */
    @Test
    public void testRegisterPayment_InvalidPaymentMethod() {
        ServiceID serviceID = new ServiceID("12345");
        UserAccount user = new UserAccount("user@example.com");
        BigDecimal amount = new BigDecimal("50.00");
        char invalidPaymentMethod = 'X'; // Método no permitido

        assertThrows(IllegalArgumentException.class, () -> registerPayment(serviceID, user, amount, invalidPaymentMethod));
    }

    /**
     * Prueba que verifica que no se puede registrar un pago sin conexión al servidor.
     */
    @Test
    public void testRegisterPayment_NoServerConnection() {
        isServerConnected = false; // Simula que el servidor no está disponible
        ServiceID serviceID = new ServiceID("12345");
        UserAccount user = new UserAccount("user@example.com");
        BigDecimal amount = new BigDecimal("25.50");
        char paymentMethod = 'C';

        assertThrows(ConnectException.class, () -> registerPayment(serviceID, user, amount, paymentMethod));
    }

    /**
     * Prueba que verifica que no se puede registrar un pago con un identificador de servicio nulo.
     */
    @Test
    public void testRegisterPayment_NullServiceID() {
        UserAccount user = new UserAccount("user@example.com");
        BigDecimal amount = new BigDecimal("30.00");
        char paymentMethod = 'P'; // 'P' para PayPal

        assertThrows(IllegalArgumentException.class, () -> registerPayment(null, user, amount, paymentMethod));
    }

    /**
     * Prueba que verifica que no se puede registrar un pago con un usuario nulo.
     */
    @Test
    public void testRegisterPayment_NullUserAccount() {
        ServiceID serviceID = new ServiceID("12345");
        BigDecimal amount = new BigDecimal("30.00");
        char paymentMethod = 'D';

        assertThrows(IllegalArgumentException.class, () -> registerPayment(serviceID, null, amount, paymentMethod));
    }

    /**
     * Prueba que verifica que no se puede registrar un pago con un importe nulo.
     */
    @Test
    public void testRegisterPayment_NullAmount() {
        ServiceID serviceID = new ServiceID("12345");
        UserAccount user = new UserAccount("user@example.com");
        char paymentMethod = 'C';

        assertThrows(IllegalArgumentException.class, () -> registerPayment(serviceID, user, null, paymentMethod));
    }

    /**
     * Prueba que verifica que no se puede registrar un pago con un método de pago vacío.
     */
    @Test
    public void testRegisterPayment_EmptyPaymentMethod() {
        ServiceID serviceID = new ServiceID("12345");
        UserAccount user = new UserAccount("user@example.com");
        BigDecimal amount = new BigDecimal("30.00");
        char emptyPaymentMethod = '\0'; // Caracter vacío

        assertThrows(IllegalArgumentException.class, () -> registerPayment(serviceID, user, amount, emptyPaymentMethod));
    }
}
