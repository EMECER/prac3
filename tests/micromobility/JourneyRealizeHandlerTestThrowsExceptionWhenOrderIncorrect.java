package micromobility;

import data.GeographicPoint;
import data.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.ProceduralException;
import services.smartfeatures.ArduinoMicroControllerDoble;
import services.smartfeatures.QRDecoderDoble;
import services.smartfeatures.Server;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyRealizeHandlerTestThrowsExceptionWhenOrderIncorrect {

    private JourneyRealizeHandler journeyRealizeHandler;
    private Server fakeServer;
    private QRDecoderDoble fakeQRDecoder;
    private ArduinoMicroControllerDoble fakeArduino;
    private BufferedImage mockQRImage;
    private UserAccount testUser;

    @BeforeEach
    void setUp() {
        fakeServer = new Server();
        fakeQRDecoder = new QRDecoderDoble();
        fakeArduino = new ArduinoMicroControllerDoble();
        testUser = new UserAccount("user@example.com");
        mockQRImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        journeyRealizeHandler = new JourneyRealizeHandler(fakeServer, fakeQRDecoder, fakeArduino);
    }

    @Test
    void testScanQR_incorrectOrder() {
        // Avanzar al paso 2
        try {
            journeyRealizeHandler.scanQR(mockQRImage, testUser);
            journeyRealizeHandler.startDriving();
        } catch (Exception e) {
            fail("No se esperaba excepción en los pasos correctos");
        }

        // Intentar escanear QR nuevamente (orden incorrecto)
        assertThrows(ProceduralException.class, () -> journeyRealizeHandler.scanQR(mockQRImage, testUser),
                "Se esperaba ProceduralException por intentar escanear QR en el paso incorrecto");
    }

    @Test
    void testStartDriving_incorrectOrder() {
        // Intentar iniciar el viaje sin escanear QR
        assertThrows(ProceduralException.class, () -> journeyRealizeHandler.startDriving(),
                "Se esperaba ProceduralException por intentar iniciar conducción sin escanear QR");
    }

    @Test
    void testStopDriving_incorrectOrder() {
        // Intentar detener el viaje sin iniciarlo
        assertThrows(ProceduralException.class, () -> journeyRealizeHandler.stopDriving(new GeographicPoint(0, 0)),
                "Se esperaba ProceduralException por intentar detener el viaje sin haberlo iniciado");
    }

    @Test
    void testUnPairVehicle_incorrectOrder() {
        // Intentar desvincular vehículo sin completar los pasos previos
        assertThrows(ProceduralException.class, () -> journeyRealizeHandler.unPairVehicle(),
                "Se esperaba ProceduralException por intentar desvincular el vehículo en el paso incorrecto");
    }
}
