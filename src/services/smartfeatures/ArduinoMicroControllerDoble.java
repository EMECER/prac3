package services.smartfeatures;

import services.exceptions.PMVPhisicalException;
import services.exceptions.ProceduralException;

import java.net.ConnectException;

public class ArduinoMicroControllerDoble implements ArduinoMicroControllerInterface{
    private boolean isBtConnected;
    private boolean isDriving;
    public ArduinoMicroControllerDoble(){
        isBtConnected = false;
        isDriving = false;
    }
    @Override
    public void setBtConnection() throws ConnectException {

    }

    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {

    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {

    }

    @Override
    public void undoBtConnection() {

    }
}
