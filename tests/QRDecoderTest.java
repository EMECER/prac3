package services.smartfeatures;

import services.exceptions.NotCorrectFormatException;
import data.PMVehicle;
import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la clase QRDecoder.
 * Proporciona tests para verificar el comportamiento esperado de los métodos getVehicleID y getVehicle,
 * incluyendo casos exitosos y manejo de excepciones.
 */
public class QRDecoderTest {

    private QRDecoder qrDecoder;
    private BufferedImage mockQRImage;
    private VehicleID expectedVehicleID;

    @BeforeEach
    public void setUp() throws NotCorrectFormatException {
        // Configuración inicial para las pruebas
        qrDecoder = new QRDecoder();
        mockQRImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        expectedVehicleID = new VehicleID(new int[]{1, 2, 3, 4}); // Simulación de ID esperado
    }

    /**
     * Prueba exitosa para decodificar una imagen de QR válida usando getVehicleID.
     */
    @Test
    public void testGetVehicleID_Success() {
        assertDoesNotThrow(() -> {
            VehicleID result = qrDecoder.getVehicleID(mockQRImage);
            assertNotNull(result, "El ID del vehículo no debería ser nulo.");
            assertEquals(expectedVehicleID, result, "El ID del vehículo decodificado debería coincidir.");
        });
    }

    /**
     * Prueba que verifica que se lanza una excepción CorruptedImgException para una imagen nula.
     */
    @Test
    public void testGetVehicleID_NullImage() {
        assertThrows(CorruptedImgException.class, () -> qrDecoder.getVehicleID(null));
    }

    /**
     * Prueba que verifica que se lanza una excepción CorruptedImgException para una imagen corrupta.
     */
    @Test
    public void testGetVehicleID_CorruptedImage() {
        // Crear una imagen inválida con dimensiones 0
        BufferedImage corruptedImage = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);

        assertThrows(CorruptedImgException.class, () -> qrDecoder.getVehicleID(corruptedImage));
    }

    /**
     * Prueba exitosa para obtener un PMVehicle usando getVehicle.
     */
    @Test
    public void testGetVehicle_Success() {
        assertDoesNotThrow(() -> {
            PMVehicle result = qrDecoder.getVehicle(mockQRImage);
            assertNotNull(result, "El objeto PMVehicle no debería ser nulo.");
            assertEquals(expectedVehicleID, result.getVehicleID(), "El ID del vehículo en PMVehicle debería coincidir.");
        });
    }

    /**
     * Prueba que verifica que se lanza una excepción CorruptedImgException en getVehicle para una imagen nula.
     */
    @Test
    public void testGetVehicle_NullImage() {
        assertThrows(CorruptedImgException.class, () -> qrDecoder.getVehicle(null));
    }

    /**
     * Prueba que verifica que se lanza una excepción NotCorrectFormatException cuando el formato del ID no es válido.
     */
    @Test
    public void testGetVehicle_InvalidFormat() {
        // Modificamos la implementación del método si es necesario para simular este caso
        // Aquí asumimos que la implementación real lanza la excepción para ciertos datos de entrada
        BufferedImage invalidQRImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        // Lanzará NotCorrectFormatException simulando un QR malformado
        assertThrows(NotCorrectFormatException.class, () -> qrDecoder.getVehicle(invalidQRImage));
    }
}
