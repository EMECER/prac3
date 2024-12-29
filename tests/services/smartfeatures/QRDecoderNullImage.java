package services.smartfeatures;

import data.VehicleIDDoble;
import data.VehicleIDInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.CorruptedImgException;
import services.exceptions.NotCorrectFormatException;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class QRDecoderNullImage {

    private QRDecoder qrDecoder;
    private BufferedImage mockQRImage;
    private VehicleIDInterface expectedVehicleID;

    @BeforeEach
    public void setUp() throws NotCorrectFormatException {
        // Configuración inicial para las pruebas
        qrDecoder = new QRDecoder();
        mockQRImage = null;
        this.expectedVehicleID = new VehicleIDDoble();
    }

    /**
     * Prueba que verifica que se lanza una excepción CorruptedImgException para una imagen nula.
     */

    @Test
    public void testGetVehicleID_NullImage() {
        assertThrows(CorruptedImgException.class, () -> qrDecoder.getVehicleID(null));
    }
}
