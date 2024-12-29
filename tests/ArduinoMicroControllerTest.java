package services.smartfeatures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.PMVPhisicalException;
import services.exceptions.ProceduralException;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la interfaz ArduinoMicroControllerInterface.
 * Proporciona tests para verificar el comportamiento esperado de los métodos definidos,
 * incluyendo casos exitosos y manejo de excepciones.
 */
public class ArduinoMicroControllerTest {

    ArduinoMicroController toTest;

    @BeforeEach
    public void setUp() {
        toTest = new ArduinoMicroController(false, false);
    }
    /**
     * Prueba exitosa para establecer una conexión Bluetooth.
     */
    @Test
    public void testSetBtConnection_Success() {
        assertDoesNotThrow(() -> toTest.setBtConnection());
        assertTrue(toTest.getConnection(), "La conexión Bluetooth debería estar activa.");
    }

    /**
     * Prueba que verifica que se lanza una excepción si la conexión Bluetooth ya está establecida.
     */
    @Test
    public void testSetBtConnection_AlreadyConnected() throws ConnectException {
        assertThrows(ConnectException.class , () -> toTest.setBtConnection());
    }

    /**
     * Prueba exitosa para iniciar la conducción.
     */
    @Test
    public void testStartDriving_Success() throws ConnectException, PMVPhisicalException, ProceduralException {
        toTest.setBtConnection(); // Necesario para poder conducir
        assertDoesNotThrow(() -> toTest.startDriving());
        assertTrue(toTest.getDrive(), "El vehículo debería estar en movimiento.");
    }

    /**
     * Prueba que verifica que no se puede iniciar la conducción sin conexión Bluetooth.
     */
    @Test
    public void testStartDriving_NoBtConnection() {
        assertThrows(ConnectException.class, () ->  toTest.startDriving());
    }

    /**
     * Prueba que verifica que no se puede iniciar la conducción si el vehículo ya está en movimiento.
     */
    @Test
    public void testStartDriving_AlreadyDriving() throws ConnectException, PMVPhisicalException, ProceduralException {
        toTest.setBtConnection();
        assertThrows(ProceduralException.class, () ->  toTest.startDriving());
    }

    /**
     * Prueba exitosa para detener la conducción.
     */
    @Test
    public void testStopDriving_Success() throws ConnectException, PMVPhisicalException, ProceduralException {
        toTest.setBtConnection();
        toTest.startDriving(); // Comienza a conducir primero
        assertDoesNotThrow(() -> toTest.stopDriving());
        assertFalse(toTest.getDrive(), "El vehículo debería estar detenido.");
    }

    /**
     * Prueba que verifica que no se puede detener la conducción sin haber iniciado previamente.
     */
    @Test
    public void testStopDriving_NotDriving() throws ConnectException {
        toTest.setBtConnection();
        assertThrows(ProceduralException.class, () -> toTest.stopDriving());
    }

    /**
     * Prueba que verifica que no se puede detener la conducción sin conexión Bluetooth.
     */
    @Test
    public void testStopDriving_NoBtConnection() {
        assertThrows(ConnectException.class,() -> toTest.stopDriving());
    }

    /**
     * Prueba exitosa para finalizar la conexión Bluetooth.
     */
    @Test
    public void testUndoBtConnection_Success() throws ConnectException, PMVPhisicalException, ProceduralException {
        toTest.setBtConnection();
        toTest.startDriving();
        toTest.undoBtConnection();
        assertFalse(toTest.getConnection(), "La conexión Bluetooth debería estar inactiva.");
        assertFalse(toTest.getDrive(), "El vehículo debería estar detenido.");
    }
}
