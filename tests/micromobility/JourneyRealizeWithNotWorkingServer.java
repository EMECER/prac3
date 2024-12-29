package micromobility;

import data.GeographicPoint;
import data.UserAccount;
import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ServerInterface;
import services.exceptions.NotCorrectFormatException;
import services.smartfeatures.ArduinoMicroControllerDoble;
import services.smartfeatures.QRDecoderDoble;
import services.smartfeatures.Server;
import services.smartfeatures.ServerDobleDown;

import java.awt.image.BufferedImage;
import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JourneyRealizeWithNotWorkingServer {
    private JourneyRealizeHandler journeyRealizeHandler;
    private ServerInterface mockServer;
    private QRDecoderDoble qrDecoderDoble;
    private ArduinoMicroControllerDoble mockArduino;
    private PMVehicle testVehicle;
    private UserAccount testUser;
    private BufferedImage mockQRImage;

    @BeforeEach
    public void setUp() throws NotCorrectFormatException {
        // Crear las dependencias necesarias
        mockServer = new ServerDobleDown(); // El servidor está por defecto en estado OK
        qrDecoderDoble = new QRDecoderDoble();
        mockArduino = new ArduinoMicroControllerDoble();



        // Crear objetos de prueba
        testVehicle = new PMVehicle(new VehicleID(1234), new GeographicPoint(0, 0));
        testUser = new UserAccount("testUser");

        // Crear el objeto de la clase a probar
        journeyRealizeHandler = new JourneyRealizeHandler(mockServer, qrDecoderDoble, mockArduino);

        // Crear una imagen de QR ficticia para la prueba
        mockQRImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    }

    @Test
    void testScanQR() throws Exception {
        // Simular el comportamiento del decodificador QR con el server defectuoso

        assertThrows(ConnectException.class,()-> journeyRealizeHandler.scanQR(mockQRImage, testUser));
    }
}
