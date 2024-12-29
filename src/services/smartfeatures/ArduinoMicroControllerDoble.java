package services.smartfeatures;

import services.exceptions.PMVPhisicalException;
import services.exceptions.ProceduralException;

import java.net.ConnectException;

public class ArduinoMicroControllerDoble implements ArduinoMicroControllerInterface{
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
