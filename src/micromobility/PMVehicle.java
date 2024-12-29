// Paquete micromobility: Implementación del caso de uso Realizar desplazamiento
package micromobility;

import data.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import services.*;
import services.smartfeatures.*;

/**
 * Clase PMVehicle
 * Representa un vehículo de micromovilidad con su estado y ubicación.
 */
public class PMVehicle {
    private PMVState state;
    private GeographicPoint location;

    private VehicleID id;

    public PMVehicle(VehicleID id, GeographicPoint location) {
        this.id = id;
        this.location = location;
    }
    public VehicleID getVehiclebyId(VehicleID id) {
        return this.PMVehicle //suposem que hi ha una base de dades
    }
    public PMVState getState() {
        return state;
    }

    public GeographicPoint getLocation() {
        return location;
    }

    public void setNotAvailb() {
        this.state = PMVState.NotAvailable;
    }

    public void setUnderWay() {
        this.state = PMVState.UnderWay;
    }

    public void setAvailb() {
        this.state = PMVState.Available;
    }

    public void setLocation(GeographicPoint gP) {
        this.location = gP;
    }
}

/**
 * Enumeración PMVState
 * Representa los posibles estados de un vehículo de micromovilidad.
 */
enum PMVState {
    Available, NotAvailable, UnderWay, TemporaryParking;
}

