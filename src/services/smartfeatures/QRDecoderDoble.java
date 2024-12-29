package services.smartfeatures;

import data.NotCorrectFormatException;
import data.VehicleID;
import services.exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

public class QRDecoderDoble implements QRDecoderInterface{
    @Override
    public VehicleID getVehicleID(BufferedImage qrImage) throws CorruptedImgException, NotCorrectFormatException {
        return new VehicleID(new int[]{1,2,3,4});
    }
}
