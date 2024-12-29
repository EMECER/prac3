package services.smartfeatures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la interfaz ArduinoMicroControllerInterface.
 * Proporciona tests para verificar el comportamiento esperado de los métodos definidos,
 * incluyendo casos exitosos y manejo de excepciones.
 */
public class ArduinoMicroControllerTest implements ArduinoMicroControllerInterface {

    private boolean isBtConnected;
    private boolean isDriving;

    @BeforeEach
    public void setUp() {
        isBtConnected = false;
        isDriving = false;
    }

    @Override
    public void setBtConnection() throws ConnectException {
        if (isBtConnected) {
            throw new ConnectException("La conexión Bluetooth ya está establecida.");
        }
        isBtConnected = true;
    }

    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!isBtConnected) {
            throw new ConnectException("Conexión Bluetooth no establecida.");
        }
        if (isDriving) {
            throw new ProceduralException("El vehículo ya está en movimiento.");
        }
        isDriving = true;
    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!isBtConnected) {
            throw new ConnectException("Conexión Bluetooth no establecida.");
        }
        if (!isDriving) {
            throw new ProceduralException("El vehículo no está en movimiento.");
        }
        isDriving = false;
    }

    @Override
    public void undoBtConnection() {
        isBtConnected = false;
        isDriving = false;
    }

    // Pruebas unitarias

    /**
     * Prueba exitosa para establecer una conexión Bluetooth.
     */
    @Test
    public void testSetBtConnection_Success() {
        assertDoesNotThrow(() -> setBtConnection());
        assertTrue(isBtConnected, "La conexión Bluetooth debería estar activa.");
    }

    /**
     * Prueba que verifica que se lanza una excepción si la conexión Bluetooth ya está establecida.
     */
    @Test
    public void testSetBtConnection_AlreadyConnected() throws ConnectException {
        setBtConnection(); // Establece la conexión inicialmente
        assertThrows(ConnectException.class, this::setBtConnection);
    }

    /**
     * Prueba exitosa para iniciar la conducción.
     */
    @Test
    public void testStartDriving_Success() throws ConnectException, PMVPhisicalException, ProceduralException {
        setBtConnection(); // Necesario para poder conducir
        assertDoesNotThrow(this::startDriving);
        assertTrue(isDriving, "El vehículo debería estar en movimiento.");
    }

    /**
     * Prueba que verifica que no se puede iniciar la conducción sin conexión Bluetooth.
     */
    @Test
    public void testStartDriving_NoBtConnection() {
        assertThrows(ConnectException.class, this::startDriving);
    }

    /**
     * Prueba que verifica que no se puede iniciar la conducción si el vehículo ya está en movimiento.
     */
    @Test
    public void testStartDriving_AlreadyDriving() throws ConnectException, PMVPhisicalException, ProceduralException {
        setBtConnection();
        startDriving(); // Inicia la conducción inicialmente
        assertThrows(ProceduralException.class, this::startDriving);
    }

    /**
     * Prueba exitosa para detener la conducción.
     */
    @Test
    public void testStopDriving_Success() throws ConnectException, PMVPhisicalException, ProceduralException {
        setBtConnection();
        startDriving(); // Comienza a conducir primero
        assertDoesNotThrow(this::stopDriving);
        assertFalse(isDriving, "El vehículo debería estar detenido.");
    }

    /**
     * Prueba que verifica que no se puede detener la conducción sin haber iniciado previamente.
     */
    @Test
    public void testStopDriving_NotDriving() throws ConnectException {
        setBtConnection();
        assertThrows(ProceduralException.class, this::stopDriving);
    }

    /**
     * Prueba que verifica que no se puede detener la conducción sin conexión Bluetooth.
     */
    @Test
    public void testStopDriving_NoBtConnection() {
        assertThrows(ConnectException.class, this::stopDriving);
    }

    /**
     * Prueba exitosa para finalizar la conexión Bluetooth.
     */
    @Test
    public void testUndoBtConnection_Success() throws ConnectException, PMVPhisicalException, ProceduralException {
        setBtConnection();
        startDriving();
        undoBtConnection();
        assertFalse(isBtConnected, "La conexión Bluetooth debería estar inactiva.");
        assertFalse(isDriving, "El vehículo debería estar detenido.");
    }
}
