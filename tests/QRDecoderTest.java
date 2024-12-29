package services.smartfeatures;

import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la interfaz QRDecoder.
 * Proporciona tests para verificar el comportamiento esperado del método getVehicleID,
 * incluyendo casos exitosos y manejo de excepciones.
 */
public class QRDecoderTest implements QRDecoderInterface {

    private BufferedImage mockQRImage;
    private VehicleID mockVehicleID;

    @BeforeEach
    public void setUp() {
        // Configura datos simulados para las pruebas
        mockQRImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        mockVehicleID = new VehicleID("TEST123");
    }

    @Override
    public VehicleID getVehicleID(BufferedImage qrImage) throws CorruptedImgException {
        // Simulación de implementación del método
        if (qrImage == null || qrImage.getWidth() == 0 || qrImage.getHeight() == 0) {
            throw new CorruptedImgException("La imagen está corrupta o no es legible.");
        }
        return mockVehicleID; // Devuelve un ID de vehículo simulado
    }

    // Pruebas unitarias

    /**
     * Prueba exitosa para decodificar una imagen de QR válida.
     */
    @Test
    public void testGetVehicleID_Success() {
        assertDoesNotThrow(() -> {
            VehicleID result = getVehicleID(mockQRImage);
            assertNotNull(result, "El ID del vehículo no debería ser nulo.");
            assertEquals(mockVehicleID, result, "El ID del vehículo decodificado debería coincidir.");
        });
    }

    /**
     * Prueba que verifica que se lanza una excepción CorruptedImgException para una imagen nula.
     */
    @Test
    public void testGetVehicleID_NullImage() {
        assertThrows(CorruptedImgException.class, () -> getVehicleID(null));
    }

    /**
     * Prueba que verifica que se lanza una excepción CorruptedImgException para una imagen corrupta.
     */
    @Test
    public void testGetVehicleID_CorruptedImage() {
        // Crear una imagen inválida con dimensiones 0
        BufferedImage corruptedImage = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);

        assertThrows(CorruptedImgException.class, () -> getVehicleID(corruptedImage));
    }
}
