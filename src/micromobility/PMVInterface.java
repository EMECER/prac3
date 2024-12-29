package micromobility;

import data.GeographicPoint;
import data.VehicleID;

public interface PMVInterface {
    public VehicleID getVehiclebyId(VehicleID id);
    public PMVehicle.PMVState getState();

    public GeographicPoint getLocation();

    public void setNotAvailb();

    public void setUnderWay();

    public void setAvailb();

    public void setLocation(GeographicPoint gP);

    public VehicleID getId();
    public void setState(PMVehicle.PMVState state);

}
