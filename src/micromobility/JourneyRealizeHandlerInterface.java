package micromobility;

import data.*;
import services.*;
import services.ServerInterface;
import services.exceptions.*;
import services.smartfeatures.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Clase JourneyRealizeHandler
 * Controladora del caso de uso Realizar desplazamiento.
 * */

public interface JourneyRealizeHandlerInterface {

    // Eventos de entrada relacionados con la interfaz de usuario
    public void scanQR(BufferedImage qrImage,UserAccount user) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException;

    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException;

    // Eventos de entrada del canal Bluetooth no vinculado
    public void broadcastStationID(StationIDInterface stID) throws ConnectException;

    // Eventos de entrada del canal del microcontrolador Arduino
    public void startDriving() throws ConnectException, ProceduralException;

    public void stopDriving(GeographicPoint endLocation) throws ConnectException, ProceduralException;

}

