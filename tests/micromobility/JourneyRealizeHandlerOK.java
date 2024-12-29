package micromobility;

import data.GeographicPoint;
import data.UserAccount;
import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.NotCorrectFormatException;
import services.smartfeatures.ArduinoMicroControllerDoble;
import services.smartfeatures.QRDecoderDoble;
import services.smartfeatures.Server;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JourneyRealizeHandlerOK {
    private JourneyRealizeHandler journeyRealizeHandler;
    private Server mockServer;
    private QRDecoderDoble qrDecoderDoble;
    private ArduinoMicroControllerDoble mockArduino;
    private PMVInterface testVehicle;
    private UserAccount testUser;
    private BufferedImage mockQRImage;

    @BeforeEach
    public void setUp() throws NotCorrectFormatException {
        // Crear las dependencias necesarias
        mockServer = new Server(); // El servidor está por defecto en estado OK
        qrDecoderDoble = new QRDecoderDoble();
        mockArduino = new ArduinoMicroControllerDoble();



        // Crear objetos de prueba
        testUser = new UserAccount("testUser");

        // Crear el objeto de la clase a probar
        journeyRealizeHandler = new JourneyRealizeHandler(mockServer, qrDecoderDoble, mockArduino);

        // Crear una imagen de QR ficticia para la prueba
        mockQRImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    }

    @Test
    void testScanQR() throws Exception {
        // Simular el comportamiento del decodificador QR
        // En este caso, ya se devuelve un vehículo con ID 1234 en el QR

        // Ejecutar el método scanQR de JourneyRealizeHandler
        journeyRealizeHandler.scanQR(mockQRImage, testUser);
        testVehicle = journeyRealizeHandler.getVehicle();

        // Comprobar que el vehículo ha sido correctamente marcado como no disponible
        assertEquals(PMVehicle.PMVState.NotAvailable, testVehicle.getState(), "El vehículo debería estar en estado No Disponible.");
    }

    @Test
    void testStartDriving() throws Exception {
        // Simular el escaneo del QR y la configuración inicial
        journeyRealizeHandler.scanQR(mockQRImage, testUser);

        // Ejecutar el método startDriving
        journeyRealizeHandler.startDriving();
        testVehicle = journeyRealizeHandler.getVehicle();

        // Verificar que el estado del vehículo se ha cambiado a "En Camino"
        assertEquals(PMVehicle.PMVState.UnderWay, testVehicle.getState(), "El vehículo debería estar en estado En Camino.");
    }

    @Test
    void testStopDriving() throws Exception {
        // Simular el escaneo del QR y la configuración inicial
        journeyRealizeHandler.scanQR(mockQRImage, testUser);
        journeyRealizeHandler.startDriving();

        // Simular una ubicación de fin de viaje
        GeographicPoint endLocation = new GeographicPoint(1, 1);

        // Ejecutar el método stopDriving
        journeyRealizeHandler.stopDriving(endLocation);
        testVehicle = journeyRealizeHandler.getVehicle();

        // Verificar que el vehículo ha vuelto a estar disponible
        assertEquals(PMVehicle.PMVState.Available, testVehicle.getState(), "El vehículo debería estar disponible.");
    }
}
