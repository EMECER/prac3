package micromobility;

import data.GeographicPoint;

public interface PMVehicleInterface {
    public PMVState getState();

    public GeographicPoint getLocation();

    public void setNotAvailb();

    public void setUnderWay();

    public void setAvailb();

    public void setLocation(GeographicPoint gP);
}
