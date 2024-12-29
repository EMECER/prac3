package services.smartfeatures;

import data.VehicleID;
import services.exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

public class QRDecoderDoble implements QRDecoderInterface{
    @Override
    public VehicleID getVehicleID(BufferedImage qrImage) throws CorruptedImgException {
        return null;
    }
}
