package services.smartfeatures;

import data.StationID;
import micromobility.JourneyRealizeHandler;
import micromobility.PMVehicle;

import java.net.ConnectException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class UnbondedBTSingnalImpl implements UnbondedBTSignalInterface{

    private JourneyRealizeHandler journeyToCheckBTSignal;
    private StationID id;

    public UnbondedBTSingnalImpl(JourneyRealizeHandler jrh, StationID id){
        this.journeyToCheckBTSignal = jrh;
        this.id = id;
    }
    @Override
    public void btBroadcast() throws ConnectException {
        while (true){
            try {
                journeyToCheckBTSignal.broadcastStationID(this.id);
                TimeUnit.SECONDS.sleep(5);
            } catch (ConnectException e){
                throw new ConnectException("Error al transmitir el id de la estación ");
            } catch (InterruptedException e) {
                throw new RuntimeException("El proceso fue interrumpido");
            }
        }
    }
}
