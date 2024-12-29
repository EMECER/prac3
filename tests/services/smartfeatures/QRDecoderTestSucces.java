package services.smartfeatures;

import data.VehicleIDDoble;
import data.VehicleIDInterface;
import micromobility.PMVehicle;
import services.exceptions.NotCorrectFormatException;

import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.CorruptedImgException;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la clase QRDecoder.
 * Proporciona tests para verificar el comportamiento esperado de los métodos getVehicleID y getVehicle,
 * incluyendo casos exitosos y manejo de excepciones.
 */
public class QRDecoderTestSucces {

    private QRDecoder qrDecoder;
    private BufferedImage mockQRImage;
    private VehicleIDInterface expectedVehicleID;

    @BeforeEach
    public void setUp() throws NotCorrectFormatException {
        // Configuración inicial para las pruebas
        qrDecoder = new QRDecoder();
        mockQRImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        this.expectedVehicleID = new VehicleIDDoble();
    }

    /**
     * Prueba exitosa para decodificar una imagen de QR válida usando getVehicleID.
     */
    @Test
    public void testGetVehicleID_Success() {
        assertDoesNotThrow(() -> {
            VehicleID result = qrDecoder.getVehicleID(mockQRImage);
            assertNotNull(result, "El ID del vehículo no debería ser nulo.");
            assertEquals(1234, result.getId(), "El ID del vehículo decodificado debería coincidir.");
        });
    }


    /**
     * Prueba exitosa para obtener un PMVehicle usando getVehicle.
     */
    @Test
    public void testGetVehicle_Success() {
        assertDoesNotThrow(() -> {
            VehicleID result = qrDecoder.getVehicle(mockQRImage).getId();
            assertNotNull(result, "El objeto PMVehicle no debería ser nulo.");
            assertEquals(1234, result.getId(), "El ID del vehículo en PMVehicle debería coincidir.");
        });
    }



}
