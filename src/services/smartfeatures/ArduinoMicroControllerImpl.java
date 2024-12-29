package services.smartfeatures;

import services.exceptions.PMVPhisicalException;
import services.exceptions.ProceduralException;

import java.net.ConnectException;

public class ArduinoMicroControllerImpl implements ArduinoMicroControllerInterface{
    private boolean isBtConnected;
    private boolean isDriving;

    public ArduinoMicroControllerImpl(){
        isBtConnected = false;
        isDriving = false;
    }
    @Override
    public void setBtConnection() throws ConnectException {
        if (isBtConnected) {
            throw new ConnectException("La conexión Bluetooth ya está establecida.");
        }
        isBtConnected = true;
    }

    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!isBtConnected) {
            throw new ConnectException("Conexión Bluetooth no establecida.");
        }
        if (isDriving) {
            throw new ProceduralException("El vehículo ya está en movimiento.");
        }
        isDriving = true;
    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!isBtConnected) {
            throw new ConnectException("Conexión Bluetooth no establecida.");
        }
        if (!isDriving) {
            throw new ProceduralException("El vehículo no está en movimiento.");
        }
        isDriving = false;
    }

    @Override
    public void undoBtConnection() {
        isBtConnected = false;
        isDriving = false;
    }

    public boolean getConnection(){
        return this.isBtConnected;
    }
    public boolean getDrive(){ return this.isDriving; }

    public void setBtConnected(boolean state){
        this.isBtConnected = state;
    }

    public void setDriving(boolean state){
        this.isDriving = state;
    }
}
