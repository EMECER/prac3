import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de prueba para la interfaz ServerInterface.
 * Proporciona tests para verificar el comportamiento esperado de los métodos definidos en ServerInterface,
 * incluyendo casos exitosos y manejo de excepciones.
 */
public class ServerInterfaceTest implements ServerInterface {

    private VehicleID mockVehicleID;
    private UserAccount mockUser;
    private StationID mockStationID;
    private GeographicPoint mockLocation;
    private JourneyService mockJourneyService;

    @BeforeEach
    public void setUp() {
        mockVehicleID = mock(VehicleID.class);
        mockUser = mock(UserAccount.class);
        mockStationID = mock(StationID.class);
        mockLocation = mock(GeographicPoint.class);
        mockJourneyService = mock(JourneyService.class);
    }

    // Implementaciones de los métodos de ServerInterface

    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {
        // Implementación simulada para propósitos de prueba
        if (vhID == null) {
            throw new PMVNotAvailException();
        }
    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException {
        // Implementación simulada para propósitos de prueba
        if (user == null || veh == null || st == null || loc == null || date == null) {
            throw new InvalidPairingArgsException();
        }
    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date,
                            float avSp, float dist, int dur, BigDecimal imp)
            throws InvalidPairingArgsException, ConnectException {
        // Implementación simulada para propósitos de prueba
        if (avSp <= 0 || dist <= 0 || dur <= 0 || imp == null) {
            throw new InvalidPairingArgsException();
        }
    }

    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {
        // Implementación simulada para propósitos de prueba
        if (user == null || veh == null || st == null || loc == null || date == null) {
            throw new IllegalArgumentException("Argumentos inválidos para el emparejamiento.");
        }
    }

    @Override
    public void unPairRegisterService(JourneyService service) throws PairingNotFoundException {
        // Implementación simulada para propósitos de prueba
        if (service == null) {
            throw new PairingNotFoundException();
        }
    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        // Implementación simulada para propósitos de prueba
        if (veh == null || st == null) {
            throw new IllegalArgumentException("Argumentos inválidos para registrar la ubicación.");
        }
    }

    // Pruebas unitarias

    /**
     * Prueba exitosa para verificar la disponibilidad de un vehículo.
     */
    @Test
    public void testCheckPMVAvail_Success() throws PMVNotAvailException, ConnectException {
        assertDoesNotThrow(() -> checkPMVAvail(mockVehicleID));
    }

    /**
     * Prueba que verifica que se lanza una excepción PMVNotAvailException cuando el vehículo no está disponible.
     */
    @Test
    public void testCheckPMVAvail_PMVNotAvailException() {
        assertThrows(PMVNotAvailException.class, () -> checkPMVAvail(null));
    }

    /**
     * Prueba exitosa para registrar un emparejamiento.
     */
    @Test
    public void testRegisterPairing_Success() throws InvalidPairingArgsException, ConnectException {
        LocalDateTime now = LocalDateTime.now();
        assertDoesNotThrow(() -> registerPairing(mockUser, mockVehicleID, mockStationID, mockLocation, now));
    }

    /**
     * Prueba que verifica que se lanza una excepción InvalidPairingArgsException cuando los argumentos son inválidos.
     */
    @Test
    public void testRegisterPairing_InvalidPairingArgsException() {
        LocalDateTime now = LocalDateTime.now();
        assertThrows(InvalidPairingArgsException.class, () -> registerPairing(null, mockVehicleID, mockStationID, mockLocation, now));
    }

    /**
     * Prueba exitosa para finalizar un emparejamiento.
     */
    @Test
    public void testStopPairing_Success() throws InvalidPairingArgsException, ConnectException {
        LocalDateTime now = LocalDateTime.now();
        float averageSpeed = 4.2f; // Velocidad en m/s
        float distanceMeters = 10000.0f; // Distancia en metros
        int duration = 20; // Duración en minutos
        BigDecimal impact = new BigDecimal("0.05");

        assertDoesNotThrow(() -> stopPairing(mockUser, mockVehicleID, mockStationID, mockLocation, now, averageSpeed, distanceMeters, duration, impact));
    }

    /**
     * Prueba que verifica que se lanza una excepción InvalidPairingArgsException al finalizar un emparejamiento con argumentos inválidos.
     */
    @Test
    public void testStopPairing_InvalidPairingArgsException() {
        LocalDateTime now = LocalDateTime.now();
        float averageSpeed = 0; // Velocidad inválida
        float distanceMeters = -10; // Distancia inválida
        int duration = 20; // Duración en minutos
        BigDecimal impact = new BigDecimal("0.05");

        assertThrows(InvalidPairingArgsException.class, () -> stopPairing(mockUser, mockVehicleID, mockStationID, mockLocation, now, averageSpeed, distanceMeters, duration, impact));
    }

    /**
     * Prueba exitosa para registrar la ubicación de un vehículo.
     */
    @Test
    public void testRegisterLocation_Success() {
        assertDoesNotThrow(() -> registerLocation(mockVehicleID, mockStationID));
    }
}
