package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.StationIDInterface;
import data.UserAccount;
import services.exceptions.*;

import java.awt.image.BufferedImage;
import java.net.ConnectException;

public class JourneyRealizeHandlerDoble implements JourneyRealizeHandlerInterface {

    boolean broadcastCalled = false;

    public JourneyRealizeHandlerDoble() {

    }
    @Override
    public void scanQR(BufferedImage qrImage, UserAccount user) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {

    }

    @Override
    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {

    }

    @Override
    public void broadcastStationID(StationIDInterface stID) throws ConnectException {
        broadcastCalled = true;  // Marcamos que el método ha sido llamado
        // Simulamos un comportamiento exitoso (sin lanzar excepciones aquí)
    }

    @Override
    public void startDriving() throws ConnectException, ProceduralException {

    }

    @Override
    public void stopDriving(GeographicPoint endLocation) throws ConnectException, ProceduralException {

    }

    public boolean getCalled(){
        return this.broadcastCalled;
    }
}
