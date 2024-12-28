package services.smartfeatures;

import java.net.ConnectException;

/**
 * Interfaz para emitir el ID de la estación a través de Bluetooth.
 */
public interface UnbondedBTSignal {

    /**
     * Emite el ID de la estación a través de Bluetooth.
     *
     * @throws ConnectException si ocurre un problema de conexión.
     */
    void btBroadcast() throws ConnectException;
}
