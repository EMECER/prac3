package services.smartfeatures;

import data.GeographicPoint;
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

        int width = qrImage.getWidth();
        int height = qrImage.getHeight();

        //Simulamos la imagen corrupta con unos valores específicos de heigth y widhth
        if (width == 999 && height == 999){
            throw new CorruptedImgException("La imagen está corrupta");
        }
        if (width != 100 || height != 100){
            throw new NotCorrectFormatException("La dimensión de la imagen debería ser 100x100");
        }

        try {
            // Simulación de decodificación del QR para obtener el VehicleID
            int id = 1234; // Supongamos que decodificamos un ID válido
            return new VehicleID(id);
        } catch (IllegalArgumentException e) {
            throw new CorruptedImgException("Error al procesar la imagen del QR.");
        }
    }

    /**
     * Decodifica una imagen de código QR para devolver el PMVehicle asociado.
     *
     * @param qrImage la imagen del código QR.
     * @return el objeto PMVehicle correspondiente al ID decodificado.
     * @throws CorruptedImgException     si la imagen está corrupta.
     * @throws NotCorrectFormatException si el formato del ID no es válido.
     */
    @Override
    public PMVehicle getVehicle(BufferedImage qrImage) throws CorruptedImgException, NotCorrectFormatException {

        //Simulamos la imagen corrupta con unos valores específicos de tamaño
        if(qrImage.getHeight() == 999 && qrImage.getWidth() == 999){
            throw new CorruptedImgException("La imagen está corrupta");
        }

        VehicleID gottenID = getVehicleID(qrImage);
        //Vehicle de ejemplo
        GeographicPoint location = new GeographicPoint(0, 0);
        PMVehicle toReturn = new PMVehicle(gottenID, location);
        return toReturn;
    }
}
