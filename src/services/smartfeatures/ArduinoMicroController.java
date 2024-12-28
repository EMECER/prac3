package services.smartfeatures;

import java.net.ConnectException;
import services.exceptions.*;

/**
 * Interfaz para gestionar microcontroladores Arduino en el sistema de micromovilidad.
 */
public interface ArduinoMicroController {

    /**
     * Establece una conexión Bluetooth.
     *
     * @throws ConnectException si ocurre un problema con la conexión.
     */
    void setBtConnection() throws ConnectException;

    /**
     * Inicia la conducción del vehículo.
     *
     * @throws PMVPhisicalException si hay un problema físico con el vehículo.
     * @throws ConnectException si hay un problema de conexión.
     * @throws ProceduralException si ocurre un error procedimental.
     */
    void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException;

    /**
     * Detiene la conducción del vehículo.
     *
     * @throws PMVPhisicalException si hay un problema físico con el vehículo.
     * @throws ConnectException si hay un problema de conexión.
     * @throws ProceduralException si ocurre un error procedimental.
     */
    void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException;

    /**
     * Finaliza la conexión Bluetooth.
     */
    void undoBtConnection();
}
