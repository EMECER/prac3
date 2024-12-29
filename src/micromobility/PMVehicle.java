// Paquete micromobility: Implementación del caso de uso Realizar desplazamiento
package micromobility;
import micromobility.*;
import data.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import services.*;
import services.smartfeatures.*;

/**
 * Clase PMVehicle
 * Representa un vehículo de micromovilidad con su estado y ubicación.
 */
public class PMVehicle implements PMVInterface{
    private PMVState state;
    private GeographicPoint location;

    private VehicleID id;

    public PMVehicle(VehicleID id, GeographicPoint location) {
        this.id = id;
        this.location = location;
        this.state = PMVState.Available;
    }
    @Override
    public VehicleID getVehiclebyId(VehicleID id) {
        return this.id; //suposem que hi ha una base de dades
    }
    @Override
    public PMVState getState() {
        return state;
    }
    @Override

    public GeographicPoint getLocation() {
        return location;
    }
    @Override
    public void setNotAvailb() {
        this.state = PMVState.NotAvailable;
    }
    @Override
    public void setUnderWay() {
        this.state = PMVState.UnderWay;
    }
    @Override
    public void setAvailb() {
        this.state = PMVState.Available;
    }
    @Override
    public void setLocation(GeographicPoint gP) {
        this.location = gP;
    }
    @Override
    public VehicleID getId() {
        return id;
    }
    @Override
    public void setState(PMVState state) {
        this.state = state;
    }

    /**
     * Enumeración PMVState
     * Representa los posibles estados de un vehículo de micromovilidad.
     */
    enum PMVState {
        Available, NotAvailable, UnderWay, TemporaryParking;
    }
}





