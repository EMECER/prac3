package services.smartfeatures;

import services.exceptions.NotCorrectFormatException;
import micromobility.PMVehicle;
import data.VehicleID;
import services.exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

/**
 * Clase que implementa la decodificación de códigos QR para obtener un PMVehicle.
 */
public class QRDecoder implements QRDecoderInterface {

    /**
     * Decodifica una imagen de código QR y extrae el ID del vehículo.
     *
     * @param qrImage la imagen del código QR.
     * @return el ID del vehículo decodificado.
     * @throws CorruptedImgException si la imagen está corrupta o no es legible.
     * @throws NotCorrectFormatException si el formato del ID no es válido.
     */
    @Override
    public VehicleID getVehicleID(BufferedImage qrImage) throws CorruptedImgException, NotCorrectFormatException {
        if (qrImage == null) {
            throw new CorruptedImgException("La imagen del código QR es nula.");
        }

        try {
            // Simulación de decodificación del QR para obtener el VehicleID
            int id = 1234; // Supongamos que decodificamos un ID válido
            return new VehicleID(id);
        } catch (Exception e) {
            throw new CorruptedImgException("Error al procesar la imagen del QR.");
        }
    }

    /**
     * Decodifica una imagen de código QR para devolver el PMVehicle asociado.
     *
     * @param qrImage la imagen del código QR.
     * @return el objeto PMVehicle correspondiente al ID decodificado.
     * @throws CorruptedImgException si la imagen está corrupta.
     * @throws NotCorrectFormatException si el formato del ID no es válido.
     */
    public PMVehicle getVehicle(BufferedImage qrImage) throws CorruptedImgException, NotCorrectFormatException {
        VehicleID vehicleID = getVehicleID(qrImage);

        // Crear un PMVehicle basado en el ID decodificado
        return new PMVehicle(vehicleID, );
    }
}
