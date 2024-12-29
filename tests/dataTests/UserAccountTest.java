package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la clase UserAccount.
 * Proporciona tests para verificar el comportamiento esperado de la clase UserAccount.
 */
public class UserAccountTest {

    private UserAccount userAccount;

    @BeforeEach
    public void setUp() {
        // Configuración inicial para las pruebas
        userAccount = new UserAccount("testUser");
    }

    /**
     * Prueba para verificar que el nombre de usuario se inicializa correctamente.
     */
    @Test
    public void testUsernameInitialization() {
        assertNotNull(userAccount, "La instancia de UserAccount no debería ser nula.");
        assertEquals("testUser", userAccount.getUsername(), "El nombre de usuario debería coincidir con el valor inicial.");
    }

    /**
     * Prueba para verificar que se puede cambiar el nombre de usuario.
     */
    @Test
    public void testSetUsername() {
        userAccount.setUsername("newUser");
        assertEquals("newUser", userAccount.getUsername(), "El nombre de usuario debería actualizarse correctamente.");
    }
}