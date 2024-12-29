package services.smartfeatures;

import data.VehicleIDDoble;
import data.VehicleIDInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.NotCorrectFormatException;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class QRDecoderWithInvalidImage {

    private QRDecoder qrDecoder;
    private BufferedImage mockQRImage;
    private VehicleIDInterface expectedVehicleID;

    @BeforeEach
    public void setUp() throws NotCorrectFormatException {
        // Configuración inicial para las pruebas
        qrDecoder = new QRDecoder();
        mockQRImage = new BufferedImage(506, 564, BufferedImage.TYPE_INT_ARGB);
        this.expectedVehicleID = new VehicleIDDoble();
    }

    /**
     * Prueba que verifica que se lanza una excepción NotCorrectFormatException cuando el formato del ID no es válido.
     */
    @Test
    public void testGetVehicle_InvalidFormat() {
        // Lanzará NotCorrectFormatException simulando un QR malformado
        assertThrows(NotCorrectFormatException.class, () -> qrDecoder.getVehicle(mockQRImage));
    }

    @Test
    public void testGetVehicleID_InvalidFormat() {
        // Lanzará NotCorrectFormatException simulando un QR malformado
        assertThrows(NotCorrectFormatException.class, () -> qrDecoder.getVehicleID(mockQRImage));
    }
}
