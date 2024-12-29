package services.smartfeatures;

import data.StationID;
import data.StationIDInterface;
import micromobility.JourneyRealizeHandler;
import micromobility.JourneyRealizeHandlerDoble;
import micromobility.JourneyRealizeHandlerInterface;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

public class UnbondedBTSingnalImpl implements UnbondedBTSignalInterface{

    private JourneyRealizeHandlerInterface journeyToCheckBTSignal;
    private StationIDInterface id;

    private int total_time;

    public UnbondedBTSingnalImpl(JourneyRealizeHandlerDoble jrh, StationIDInterface id){
        this.journeyToCheckBTSignal = jrh;
        this.id = id;
    }
    @Override
    public void btBroadcast() throws ConnectException {
        while (true){
            try {
                journeyToCheckBTSignal.broadcastStationID(this.id);
                TimeUnit.SECONDS.sleep(3);
                total_time += 3;

                //Para probar el caso en el que haya un error, supondremos que después de 10 segundos tiraremos la excepción
                if (total_time > 10){
                    throw new ConnectException("Error al transmitir el id de la estación ");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("El proceso fue interrumpido");
            }
        }
    }
}
