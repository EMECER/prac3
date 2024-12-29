package services.smartfeatures;

import services.exceptions.NotCorrectFormatException;
import data.VehicleID;
import java.awt.image.BufferedImage;
import services.exceptions.*;

/**
 * Interfaz para decodificar códigos QR y extraer información del vehículo.
 */
public interface QRDecoderInterface {

    /**
     * Decodifica una imagen de código QR y extrae el ID del vehículo.
     *
     * @param qrImage la imagen del código QR.
     * @return el ID del vehículo decodificado.
     * @throws CorruptedImgException si la imagen está corrupta o no es legible.
     */
    VehicleID getVehicleID(BufferedImage qrImage) throws CorruptedImgException, NotCorrectFormatException;
}
