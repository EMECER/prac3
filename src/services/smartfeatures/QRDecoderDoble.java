package services.smartfeatures;

import micromobility.PMVehicle;
import services.exceptions.NotCorrectFormatException;
import data.VehicleID;
import services.exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

public class QRDecoderDoble implements QRDecoderInterface{
    @Override
    public VehicleID getVehicleID(BufferedImage qrImage) throws CorruptedImgException, NotCorrectFormatException {
        return new VehicleID(1234);
    }

    @Override
    public PMVehicle getVehicle(BufferedImage qrImage) {
        return null;
    }
}
