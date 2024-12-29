package services.smartfeatures;

import services.exceptions.NotCorrectFormatException;
import data.VehicleID;
import services.exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

public class QRDecoderDoble implements QRDecoderInterface{
    @Override
    public VehicleID getVehicleID(BufferedImage qrImage) throws CorruptedImgException, NotCorrectFormatException {
        return new VehicleID(1234);
    }
}
