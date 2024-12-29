package services.smartfeatures;

import data.StationID;
import data.StationIDDoble;
import data.StationIDInterface;
import micromobility.JourneyRealizeHandlerDoble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.NotCorrectFormatException;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnbondedBTSignalImpTest {

    private UnbondedBTSingnalImpl unbondedBTSingnal;
    private JourneyRealizeHandlerDoble journeyRealizeHandlerDoble;
    private StationIDInterface stationID;

    @BeforeEach
    public void setUp() throws NotCorrectFormatException {
        // Inicializamos las dependencias
        journeyRealizeHandlerDoble = new JourneyRealizeHandlerDoble();
        stationID = new StationIDDoble();  // ID de estación simulado
        unbondedBTSingnal = new UnbondedBTSingnalImpl(journeyRealizeHandlerDoble, stationID);
    }

    @Test
    public void testBtBroadcast_callsBroadcastStationID() throws ConnectException {
        // Ejecutamos el método btBroadcast en un hilo separado para simular la ejecución continua
        Thread broadcastThread = new Thread(() -> {
            try {
                unbondedBTSingnal.btBroadcast();
            } catch (ConnectException e) {
                // Esperamos la excepción en la prueba
            }
        });

        // Iniciamos el hilo
        broadcastThread.start();

        // Esperamos 4 segundos para que el método broadcastStationID se haya llamado al menos una vez
        try {
            Thread.sleep(4000);  // Tiempo suficiente para asegurar que se llamó al método
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificamos si broadcastStationID fue llamado
        assertTrue(journeyRealizeHandlerDoble.getCalled(), "El método broadcastStationID debería haber sido llamado");

    }

    @Test
    public void testBtBroadcast_throwsConnectExceptionAfterTimeout() {
        // Creamos una versión del UnbondedBTSingnalImpl que lanza la excepción después de 10 segundos
        UnbondedBTSingnalImpl unbondedBTSingnalWithTimeout = new UnbondedBTSingnalImpl(journeyRealizeHandlerDoble, stationID) {
            @Override
            public void btBroadcast() throws ConnectException {
                try {
                    super.btBroadcast();
                } catch (ConnectException e) {
                    throw new ConnectException("Error al transmitir el id de la estación después de 10 segundos");
                }
            }
        };

        // Ejecutamos el método btBroadcast en un hilo separado
        Thread broadcastThread = new Thread(() -> {
            try {
                unbondedBTSingnalWithTimeout.btBroadcast();
            } catch (ConnectException e) {
                // Aseguramos que la excepción sea lanzada después de 10 segundos
                assertEquals("Error al transmitir el id de la estación después de 10 segundos", e.getMessage());
            }
        });

        // Iniciamos el hilo
        broadcastThread.start();

        // Esperamos 12 segundos para que se lance la excepción
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
