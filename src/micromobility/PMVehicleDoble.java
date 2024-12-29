package micromobility;

import data.GeographicPoint;
import data.VehicleID;
import data.VehicleIDDoble;
import data.VehicleIDInterface;
import services.exceptions.NotCorrectFormatException;

public class PMVehicleDoble implements PMVInterface{
    private PMVehicle.PMVState state;
    private GeographicPoint location;

    private VehicleIDInterface id;
    public PMVehicleDoble() throws NotCorrectFormatException {
        this.id = new VehicleIDDoble();
        this.location = new GeographicPoint(0, 0);
        this.state = PMVehicle.PMVState.Available;
    }
    @Override
    public VehicleID getVehiclebyId(VehicleID id) {
        return null;
    }

    @Override
    public PMVehicle.PMVState getState() {
        return null;
    }

    @Override
    public GeographicPoint getLocation() {
        return null;
    }

    @Override
    public void setNotAvailb() {

    }

    @Override
    public void setUnderWay() {

    }

    @Override
    public void setAvailb() {

    }

    @Override
    public void setLocation(GeographicPoint gP) {

    }

    @Override
    public VehicleID getId() {
        return null;
    }

    @Override
    public void setState(PMVehicle.PMVState state) {

    }
}
